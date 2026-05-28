package dvachmovie.fragment.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import dvachmovie.AppConfig
import com.dvachmovie.android.R
import dvachmovie.architecture.base.BaseFragment
import dvachmovie.architecture.binding.BindingCache
import com.dvachmovie.android.databinding.FragmentSettingsBinding
import dvachmovie.di.core.FragmentComponent
import dvachmovie.di.core.Injector
import dvachmovie.worker.WorkerManager

class SettingsFragment : BaseFragment<SettingsVM>(SettingsVM::class) {

    override fun inject(component: FragmentComponent) = Injector.viewComponent().inject(this)

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        viewModel.addFields()
        setHasOptionsMenu(true)
        configureVM()

        return binding.root
    }

    private fun configureVM() {
        when (viewModel.getCurrentBaseUrl()) {
            AppConfig.DVACH_URL -> {
                viewModel.isDvachBoardsVisible.value = true
                viewModel.isFourChanBoardsVisible.value = false
                viewModel.isNeoChanBoardsVisible.value = false
            }

            AppConfig.FOURCHAN_URL -> {
                viewModel.isDvachBoardsVisible.value = false
                viewModel.isFourChanBoardsVisible.value = true
                viewModel.isNeoChanBoardsVisible.value = false
            }

            AppConfig.NEOCHAN_URL -> {
                viewModel.isDvachBoardsVisible.value = false
                viewModel.isFourChanBoardsVisible.value = false
                viewModel.isNeoChanBoardsVisible.value = true
            }
        }

        viewModel.routeToStartFragment = {
            router.navigateSettingsToStartFragment(it)
        }

        viewModel.recreateMoviesDB = {
            logger.d(this.javaClass.name, "refresh database")
            BindingCache.media = listOf()
            WorkerManager.deleteAllInDB(requireContext(), this) {
                viewModel.reInitMovies(false)
            }
        }
        viewModel.setReportBtnVisible()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.root.findViewById(R.id.toolbar))
        viewModel.version.observe(viewLifecycleOwner) {
            binding.versionText.text = it
        }

        viewModel.isGestureEnabled.observe(viewLifecycleOwner) { isEnabled ->
            binding.primarySettingsLayout.gestureSwitch.isChecked = isEnabled
        }
        binding.primarySettingsLayout.gestureSwitch.setOnCheckedChangeListener(viewModel.onGestureLoadingClicked)

        viewModel.isReportBtnShow.observe(viewLifecycleOwner) { isEnabled ->
            binding.primarySettingsLayout.reportBtnVisibilitySwitch.isChecked = isEnabled
        }
        binding.primarySettingsLayout.reportBtnVisibilitySwitch.setOnCheckedChangeListener(viewModel.onReportSwitchClicked)
        viewModel.isReportBtnVisible.observe(viewLifecycleOwner) { isVisible ->
            binding.primarySettingsLayout.reportBtnVisibilitySwitch.isVisible = isVisible
        }

        viewModel.isListBtnShow.observe(viewLifecycleOwner) { isEnabled ->
            binding.primarySettingsLayout.listBtnVisibilitySwitch.isChecked = isEnabled
        }
        binding.primarySettingsLayout.listBtnVisibilitySwitch.setOnCheckedChangeListener(viewModel.onListSwitchClicked)

        binding.primarySettingsLayout.buttonCleanDatabase.setOnClickListener(viewModel.onCleanDatabase)
        binding.primarySettingsLayout.buttonRefreshMovies.setOnClickListener(viewModel.onRefreshMovies)
        binding.primarySettingsLayout.buttonImageBoard.setOnClickListener(viewModel.onSetImageBoard)

        viewModel.isDvachBoardsVisible.observe(viewLifecycleOwner) { isVisible ->
            binding.primarySettingsLayout.dvachBoards.linearLayout.isVisible = isVisible
        }
        viewModel.isFourChanBoardsVisible.observe(viewLifecycleOwner) { isVisible ->
            binding.primarySettingsLayout.fourchanBoards.fourchanRoot.isVisible = isVisible
        }
        viewModel.isNeoChanBoardsVisible.observe(viewLifecycleOwner) { isVisible ->
            binding.primarySettingsLayout.neochanBoards.neochanRoot.isVisible = isVisible
        }
        binding.primarySettingsLayout.neochanBoards.btnNeoChanBoard.setOnClickListener(viewModel.onSetNeoChanBoard)

        binding.primarySettingsLayout.dvachBoards.buttonPopularBoard.setOnClickListener(viewModel.onSetDvachPopularBoard)
        binding.primarySettingsLayout.dvachBoards.buttonThemeBoard.setOnClickListener(viewModel.onSetDvachThemeBoard)
        binding.primarySettingsLayout.dvachBoards.buttonGamesBoard.setOnClickListener(viewModel.onSetDvachGamesBoard)
        binding.primarySettingsLayout.dvachBoards.buttonPolNewsBoard.setOnClickListener(viewModel.onSetDvachPolNewsBoard)
        binding.primarySettingsLayout.dvachBoards.buttonTechSoftBoard.setOnClickListener(viewModel.onSetDvachTechSoftBoard)
        binding.primarySettingsLayout.dvachBoards.buttonOtherAdultBoard.setOnClickListener(viewModel.onSetDvachAdultOtherBoard)
        binding.primarySettingsLayout.dvachBoards.buttonJapanBoard.setOnClickListener(viewModel.onSetDvachJapanBoard)
        binding.primarySettingsLayout.dvachBoards.buttonCreationBoard.setOnClickListener(viewModel.onSetDvachCreationBoard)
        binding.primarySettingsLayout.dvachBoards.buttonAdultBoard.setOnClickListener(viewModel.onSetDvachAdultBoard)

        binding.primarySettingsLayout.fourchanBoards.btnFourChanJapanCultureBoard.setOnClickListener(
            viewModel.onSetFourChanJapanCultureBoard
        )
        binding.primarySettingsLayout.fourchanBoards.btnFourChanVideoGamesBoard.setOnClickListener(
            viewModel.onSetFourChanVideoGamesBoard
        )
        binding.primarySettingsLayout.fourchanBoards.btnFourChanCreativeBoard.setOnClickListener(
            viewModel.onSetFourChanCreativeBoard
        )
        binding.primarySettingsLayout.fourchanBoards.btnFourChanInterestsBoard.setOnClickListener(
            viewModel.onSetFourChanInterestsBoard
        )
        binding.primarySettingsLayout.fourchanBoards.btnFourChanOtherBoard.setOnClickListener(
            viewModel.onSetFourChanOtherBoard
        )
        binding.primarySettingsLayout.fourchanBoards.btnFourChanMiscBoard.setOnClickListener(
            viewModel.onSetFourChanMiscBoard
        )
        binding.primarySettingsLayout.fourchanBoards.btnFourChanAdultBoard.setOnClickListener(
            viewModel.onSetFourChanAdultBoard
        )


    }
}
