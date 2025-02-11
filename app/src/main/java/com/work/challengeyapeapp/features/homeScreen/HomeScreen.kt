package com.work.challengeyapeapp.features.homeScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.work.challengeyapeapp.features.navigation.AppScreens
import com.work.challengeyapeapp.R
import com.work.challengeyapeapp.core.common.GlobalConstants
import com.work.challengeyapeapp.domain.model.RecipeModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
internal fun HomeScreen(viewModel: HomeScreenViewModel, navController: NavController) {

    val resultState by viewModel.recipeListState.collectAsStateWithLifecycle()
    val recipeName by viewModel.recipeName.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    Scaffold(topBar = { HomeScreenTopBar() }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            SearchBox(searchText = recipeName, onSearchChanged = viewModel::setQuery)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.size_8)))
            when {
                resultState.isLoading -> LoadingScreen()
                resultState.error.isNotBlank() -> ErrorScreen(resultState.error)
                else -> resultState.data?.let { recipes ->
                    SearchResultsList(
                        recipes = recipes,
                        listState = listState,
                        navController = navController
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchBox(searchText: String, onSearchChanged: (String) -> Unit) {
    TextField(
        value = searchText,
        onValueChange = onSearchChanged,
        label = { Text(text = stringResource(R.string.homeScreen_search)) },
        leadingIcon = {
            Icon(
                Icons.Outlined.Search,
                contentDescription = stringResource(R.string.homeScreen_content)
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.size_12))
            .padding(top = dimensionResource(R.dimen.size_20))
            .heightIn(max = dimensionResource(R.dimen.size_80))
            .fillMaxWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.size_12)),
        textStyle = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
fun SearchResultsList(
    recipes: List<RecipeModel>,
    listState: LazyListState,
    navController: NavController
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.padding(
            horizontal = dimensionResource(R.dimen.size_18),
            vertical = dimensionResource(R.dimen.size_8)
        )
    ) {
        items(recipes.size) { index ->
            RecipeItem(recipe = recipes[index], onClick = {
                navController.navigate(AppScreens.DetailScreen.route + "/${recipes[index].id}")
            })
        }
    }
}

@Composable
fun RecipeItem(recipe: RecipeModel, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(dimensionResource(R.dimen.size_8)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.size_2)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.size_8))
            .clickable(onClick = onClick)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(recipe.image)
                    .crossfade(true).build(),
                contentDescription = null,
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.size_110))
                    .width(dimensionResource(id = R.dimen.size_100)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.size_10))) {
                Text(
                    text = recipe.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.size_4)))
                Text(text = recipe.category, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.size_4)))
                Text(
                    text = "ID: ${recipe.id}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreenTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.homeScreen_title),
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(error: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(R.dimen.size_60)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = GlobalConstants.WITH_OUT_SEARCHS + "/" + error,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(dimensionResource(R.dimen.size_16))
        )
    }
}
