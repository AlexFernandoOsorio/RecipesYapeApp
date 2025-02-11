package com.work.challengeyapeapp.features.detailScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.work.challengeyapeapp.core.common.ResultType
import com.work.challengeyapeapp.domain.usecase.GetRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class DetailScreenViewModel @Inject constructor(
    private val getRecipeUseCase: GetRecipeUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    private val _recipeState = MutableStateFlow(DetailScreenState())
    val recipeState: StateFlow<DetailScreenState> = _recipeState.asStateFlow()

    init {
        savedStateHandle.getLiveData<String>("id")
            .asFlow()
            .filterNotNull()
            .onEach { id ->
                getRecipeDetail(id)
            }
            .launchIn(viewModelScope)
    }

    fun getRecipeDetail(value: String) {
        _recipeState.value = DetailScreenState(isLoading = true)
        getRecipeUseCase(value).onEach { result ->
            when (result) {
                is ResultType.Success -> {
                    val data = result.value
                    _recipeState.value = DetailScreenState(data = data)
                }

                is ResultType.Error -> {
                    _recipeState.value = DetailScreenState(error = result.value.toString())
                }
            }
        }
            .flowOn(dispatcher)
            .launchIn(viewModelScope)
    }
}
