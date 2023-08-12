import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.produce.project.mobile.viewmodel.DKMPViewModel
import com.produce.project.composables.CommonTheme
import com.produce.project.mobile.viewmodel.getDesktopInstance


fun main() = application {
    Window(title = "Default", onCloseRequest = ::exitApplication) {
        CommonTheme(DKMPViewModel.Factory.getDesktopInstance())
    }
}