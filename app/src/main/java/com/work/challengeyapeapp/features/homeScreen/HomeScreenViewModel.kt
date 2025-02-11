package com.work.challengeyapeapp.features.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.work.challengeyapeapp.core.common.GlobalConstants
import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.domain.usecase.GetRecipeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeScreenViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val _recipeListState = MutableStateFlow(HomeScreenState())
    val recipeListState: StateFlow<HomeScreenState> = _recipeListState.asStateFlow()

    private val _recipeName: MutableStateFlow<String> = MutableStateFlow("")
    val recipeName: StateFlow<String> get() = _recipeName

    fun setQuery(param: String) {
        _recipeName.value = param
    }

    init {
        viewModelScope.launch {
            _recipeName.debounce(GlobalConstants.TIME_OUT).collectLatest {
                getRecipesList(it)
            }
        }
    }

    fun getRecipesList(name: String) {
        _recipeListState.value = HomeScreenState(isLoading = true)
        getRecipeListUseCase.invoke(name).onEach { result ->
            when (result) {
                is ResultType.Success -> {
                    val data = result.value
                    _recipeListState.update {  HomeScreenState(data = data)}
                }

                is ResultType.Error -> {
                    _recipeListState.value = HomeScreenState(error = result.value.toString())
                }
            }
        }
            .flowOn(dispatcher)
            .launchIn(viewModelScope)
    }
}
