package com.rafalesan.credikiosko.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rafalesan.credikiosko.core.commons.presentation.theme.CrediKioskoTheme
import dagger.hilt.android.AndroidEntryPoint
import com.rafalesan.credikiosko.core.R as CoreR

@AndroidEntryPoint
class LoginFragment : Fragment() {

    val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(CoreR.layout.compose_layout, container, false)
        val composeView = view.findViewById<ComposeView>(CoreR.id.compose_view)
        val navController = findNavController()
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                CrediKioskoTheme {
                    LoginScreen(navController, viewModel)
                }
            }
        }

        return view
    }


}
