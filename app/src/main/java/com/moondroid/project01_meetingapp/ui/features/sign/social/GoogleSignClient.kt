package com.moondroid.project01_meetingapp.ui.features.sign.social

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.moondroid.damoim.common.Constants.DEFAULT_PROFILE_IMG
import com.moondroid.damoim.common.debug
import com.moondroid.damoim.common.logException
import com.moondroid.project01_meetingapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID

class GoogleSignClient(private val context: Context, private val socialSignEventListener: SocialSignEventListener) {

    fun getGoogleAccount(credentialLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>?) {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce = digest.fold("") { acc, byte ->
            acc + "%02x".format(byte)
        }

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(context.getString(R.string.google_credential_server_id))
            .setAutoSelectEnabled(true)
            .setNonce(hashedNonce)
            .build()

        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        CoroutineScope(Dispatchers.Default).launch {
            try {
                val result = CredentialManager.create(context).getCredential(
                    context = context,
                    request = request
                )
                handleSignIn(result)

            } catch (e: NoCredentialException) {
                credentialLauncher?.let {
                    val intent = Intent(Settings.ACTION_ADD_ACCOUNT)
                    intent.putExtra(Settings.EXTRA_ACCOUNT_TYPES, arrayOf("com.google"))
                    credentialLauncher.launch(intent)
                }.run {
                    socialSignEventListener.onError(e)
                }
            } catch (e: GetCredentialException) {
                socialSignEventListener.onError(e)
            }
        }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential
        when (credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                        /*val id = googleIdTokenCredential.id
                        val name = googleIdTokenCredential.displayName.toString()
                        val thumb = if (googleIdTokenCredential.profilePictureUri == null) {
                            DEFAULT_PROFILE_IMG
                        } else {
                            googleIdTokenCredential.profilePictureUri.toString()
                        }*/
                        //viewModel.signInSocial(id, name, thumb)
                        //
                        val googleIdToken = googleIdTokenCredential.idToken
                        val authCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                        val user = Firebase.auth.signInWithCredential(authCredential).await().user
                        user?.let {
                            val id = it.uid
                            val name = it.displayName.toString()
                            val thumb = it.photoUrl?.toString() ?: DEFAULT_PROFILE_IMG

                            socialSignEventListener.onSuccess(SocialSignData(id, name, thumb))
                        }
                    } catch (e: GoogleIdTokenParsingException) {
                        logException(e)
                    }
                }
            }

            else -> {
                socialSignEventListener.onError(Exception("지원하지 않는 자격 증명 방식입니다.\n다른 방법으로 로그인해주세요."))
            }
        }
    }
}