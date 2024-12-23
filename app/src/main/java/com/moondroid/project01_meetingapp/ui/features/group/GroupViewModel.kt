package com.moondroid.project01_meetingapp.ui.features.group

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.google.firebase.database.ChildEvent
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.moondroid.damoim.common.constant.ResponseCode
import com.moondroid.damoim.common.util.debug
import com.moondroid.damoim.domain.model.ChatItem
import com.moondroid.damoim.domain.model.status.onError
import com.moondroid.damoim.domain.model.status.onFail
import com.moondroid.damoim.domain.model.status.onSuccess
import com.moondroid.damoim.domain.model.status.onSuccessWithoutResult
import com.moondroid.damoim.domain.usecase.group.GetFavorUseCase
import com.moondroid.damoim.domain.usecase.group.GetGroupDetailUseCase
import com.moondroid.damoim.domain.usecase.group.GetMembersUseCase
import com.moondroid.damoim.domain.usecase.group.SaveRecentUseCase
import com.moondroid.damoim.domain.usecase.group.SetFavorUseCase
import com.moondroid.damoim.domain.usecase.moim.GetMoimsUseCase
import com.moondroid.damoim.domain.usecase.profile.DeleteProfileUseCase
import com.moondroid.damoim.domain.usecase.profile.GetProfileUseCase
import com.moondroid.project01_meetingapp.core.base.BaseViewModel
import com.moondroid.project01_meetingapp.core.navigation.GroupRoot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProfileUseCase: GetProfileUseCase,
    private val getGroupDetailUseCase: GetGroupDetailUseCase,
    private val getFavorUseCase: GetFavorUseCase,
    private val setFavorUseCase: SetFavorUseCase,
    private val saveRecentUseCase: SaveRecentUseCase,
    private val getMembersUseCase: GetMembersUseCase,
    private val getMoimsUseCase: GetMoimsUseCase,
) : BaseViewModel<GroupContract.State, GroupContract.Event, GroupContract.Effect>(GroupContract.State()) {

    val title = mutableStateOf("")

    init {
        title.value = savedStateHandle.toRoute<GroupRoot>().title
        getProfile()
        getGroupDetail()
        getMembers()
        getMoims()
        getFavor()
        saveRecent()
    }

    private fun getProfile() {
        viewModelScope.launch {
            getProfileUseCase().collect { result ->
                result.onSuccess {
                    setState { copy(profile = it) }
                }.onFail {
                    setEffect(GroupContract.Effect.Expired)
                }.onError {
                    setEffect(GroupContract.Effect.Expired)
                }
            }
        }
    }

    override suspend fun handleEvent(event: GroupContract.Event) {
        when (event) {
            GroupContract.Event.Join -> TODO()
            is GroupContract.Event.UserProfile -> TODO()
            GroupContract.Event.ToggleFavor -> toggleFavor()
            GroupContract.Event.ImageFetch -> getImage()
            GroupContract.Event.ChatFetch -> chat()
        }
    }

    private fun getGroupDetail() {
        viewModelScope.launch {
            getGroupDetailUseCase(title.value).collect { result ->
                result.onSuccess {
                    setState { copy(groupDetail = it) }
                }.onFail {
                    setEffect(GroupContract.Effect.Expired)
                }.onError {

                }
            }
        }
    }

    private fun getMembers() {
        viewModelScope.launch {
            getMembersUseCase(title.value).collect { result ->
                result.onSuccess {
                    setState { copy(members = it) }
                }.onFail {
                    setEffect(GroupContract.Effect.Expired)
                }.onError {

                }
            }
        }
    }

    private fun getMoims() {
        viewModelScope.launch {
            getMoimsUseCase(title.value).collect { result ->
                result.onSuccess {
                    setState { copy(moims = it) }
                }.onFail {
                    setEffect(GroupContract.Effect.Expired)
                }.onError {

                }
            }
        }
    }

    private fun getFavor() {
        viewModelScope.launch {
            getFavorUseCase(title.value).collect { result ->
                result.onSuccess {
                    setState { copy(isFavor = it) }
                }.onError {

                }.onFail {
                    setEffect(GroupContract.Effect.Expired)
                }
            }
        }
    }

    private fun toggleFavor() {
        viewModelScope.launch {
            setFavorUseCase(title.value, !uiState.value.isFavor).collect { result ->
                result.onSuccessWithoutResult {
                    getFavor()
                }
            }
        }
    }

    private fun saveRecent() {
        viewModelScope.launch {
            saveRecentUseCase(title.value, System.currentTimeMillis().toString()).collect { result ->
                result.onFail {
                    if (it == ResponseCode.PROFILE_ERROR) {
                        setEffect(GroupContract.Effect.Expired)
                    }
                }
            }
        }
    }

    private fun getImage() {
        debug("getImage()")
        val firebaseDB = FirebaseDatabase.getInstance()
        val galleryRef = firebaseDB.getReference("GalleryImgs/${uiState.value.groupDetail.title}")
        galleryRef.get().addOnSuccessListener {
            val urlList = mutableListOf<String>()
            it.children.forEach { ds ->
                ds.getValue(String::class.java)?.let { url ->
                    urlList.add(url)
                }
            }
            setState { copy(images = urlList.toList()) }
        }
    }

    private val chatListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            snapshot.getValue(ChatItem::class.java)?.let { chat ->
                val member = uiState.value.members.find { chat.id == it.id }
                member?.let {
                    chat.thumb = member.thumb
                    chat.name = member.name
                    chat.other = chat.id != uiState.value.profile.id
                    uiState.value.chats.add(chat)
                }
            }
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildRemoved(snapshot: DataSnapshot) {

        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    private fun chat() {
        val firebaseDB = FirebaseDatabase.getInstance()
        val chatRef = firebaseDB.getReference("chat/" + uiState.value.groupDetail.title)
        chatRef.addChildEventListener(chatListener)
    }
}