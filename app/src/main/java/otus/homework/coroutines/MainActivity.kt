package otus.homework.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()

    private var catsView: CatsView? = null

    private val viewModel: CatsViewModel by viewModels(
        factoryProducer = {
            CatsViewModelFactory(
                application,
                diContainer.service
            )
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        catsView = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        catsView?.let { view ->
            setContentView(view)

            view.viewModel = viewModel
            viewModel.attachView(view)
            viewModel.onInitComplete()
        }
    }

    override fun onStop() {
        if (isFinishing) {
            catsView?.stopPictureLoading()
        }
        super.onStop()
    }
}