package com.work.challengeyapeapp.features.detailScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.work.challengeyapeapp.core.common.GlobalConstants
import com.work.challengeyapeapp.features.navigation.AppScreens
import com.work.challengeyapeapp.R
import com.work.challengeyapeapp.domain.model.RecipeModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun DetailScreen(
    idRecipe: String,
    viewModel: DetailScreenViewModel,
    navController: NavController
) {

    val resultState by viewModel.recipeState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = { DetailScreenTopBar(navController) },
        floatingActionButton = {
            resultState.data?.let { recipe ->
                FloatingActionButtonMap(
                    recipe = recipe[0],
                    navController = navController,
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                resultState.isLoading -> LoadingScreen()
                resultState.error.isNotBlank() -> ErrorScreen(idRecipe, resultState.error)
                else -> resultState.data?.let { recipe ->
                    RecipeContent(recipe, listState)
                }
            }
        }
    }

}

@Composable
fun RecipeContent(
    oneRecipeList: List<RecipeModel>,
    listState: LazyListState
) {
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.size_16)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.size_12))
    ) {
        items(oneRecipeList.size) { index ->
            RecipeItem(oneRecipe = oneRecipeList[index])
        }
    }
}

@Composable
fun RecipeItem(oneRecipe: RecipeModel) {
    Image(
        painter = rememberAsyncImagePainter(oneRecipe.image),
        contentDescription = stringResource(R.string.detailScreen_detail),
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.size_250)),
        contentScale = ContentScale.Crop
    )

    Text(
        text = oneRecipe.name,
        style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(vertical = dimensionResource(R.dimen.size_8))
    )

    Text(
        text = stringResource(R.string.detailScreen_category_label) + oneRecipe.category,
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth().padding(vertical = dimensionResource(R.dimen.size_8))
    )

    Text(
        text = stringResource(R.string.detailScreen_Description),
        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth().padding(vertical = dimensionResource(R.dimen.size_8))
    )

    Text(
        text = oneRecipe.description,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.fillMaxWidth().padding(vertical = dimensionResource(R.dimen.size_8))
    )
}

@Composable
fun FloatingActionButtonMap(recipe: RecipeModel, navController: NavController) {
    FloatingActionButton(
        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.size_40)),
        onClick = {
        navController.navigate(AppScreens.MapScreen.route + "/${recipe.location}")
    }) {
        Icon(
            Icons.Filled.LocationOn,
            contentDescription = stringResource(R.string.detailScreen_map_label)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTopBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.detailScreen_detail), color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Color.White)
            }
        }
    )
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = dimensionResource(R.dimen.size_100)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(idRecipe: String, error: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = dimensionResource(R.dimen.size_60)),
        contentAlignment = Alignment.Center
    ) {
        Text(text = GlobalConstants.WITH_OUT_SEARCH_RECIPE + idRecipe + "/" + error,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.size_16)))
    }
}
