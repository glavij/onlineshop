package com.onlineshop.auth

import ContentWithMessageBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.onlineshop.auth.component.GoogleButton
import com.onlineshop.shared.Alpha
import com.onlineshop.shared.BebasNeueFont
import com.onlineshop.shared.FontSize
import com.onlineshop.shared.Surface
import com.onlineshop.shared.SurfaceBrand
import com.onlineshop.shared.SurfaceError
import com.onlineshop.shared.TextPrimary
import com.onlineshop.shared.TextSecondary
import com.onlineshop.shared.TextWhite
import rememberMessageBarState

@Composable
fun AuthScreen() {
    val messageBarState = rememberMessageBarState()
    var loadingState by remember { mutableStateOf(false) }

    Scaffold { padding ->
         ContentWithMessageBar(
             contentBackgroundColor = Surface,
             modifier = Modifier
                 .padding(
                     top =  padding.calculateTopPadding(),
                     bottom = padding.calculateBottomPadding()
                 ),
             messageBarState = messageBarState,
             errorMaxLines = 2,
             errorContainerColor = SurfaceError,
             errorContentColor = TextWhite,
             successContainerColor = SurfaceBrand,
             successContentColor = TextPrimary

         ){
            Column(modifier = Modifier.fillMaxSize().padding(all = 24.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "NUTRISPORT",
                        textAlign = TextAlign.Center,
                        fontFamily = BebasNeueFont(),
                        fontSize = FontSize.EXTRA_LARGE,
                        color = TextSecondary
                    )


                    Text(modifier = Modifier.fillMaxWidth().alpha(Alpha.HALF),
                        text = "Sing in to continue",
                        textAlign = TextAlign.Center,
                        fontSize = FontSize.EXTRA_REGULAR,
                        color = TextPrimary
                        )

                }
                GoogleButtonUiContainerFirebase(
                    linkAccount = false,
                    onResult = { result ->
                         result.onSuccess { user ->
                             messageBarState.addSuccess("Authentication successful!!")
                             loadingState = false
                         }.onFailure { error->
                            if(error.message?.contains("A network error") == true){
                                messageBarState.addError("Internet connection unavailable.")
                            } else if (error.message?.contains("Idtoken is null") == true) {
                                println(error.message)
                                messageBarState.addError("Sing in canceled.")
                            }else {
                                messageBarState.addError(error.message ?: "Unknown")
                            }
                             loadingState = false
                         }
                    }


                ){
                    GoogleButton (
                        loading = loadingState,
                        onClick = {
                            loadingState = true
                             this@GoogleButtonUiContainerFirebase.onClick()
                        }
                    )
                }
            }
         }
    }
}