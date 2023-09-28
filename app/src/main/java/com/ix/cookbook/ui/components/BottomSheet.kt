package com.ix.cookbook.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ix.cookbook.R
import com.ix.cookbook.data.requestUtil.filters.DietTypeFilter
import com.ix.cookbook.data.requestUtil.filters.Filter
import com.ix.cookbook.data.requestUtil.filters.MealTypeFilter
import com.ix.cookbook.screens.recipes.defaultDietTypeFilters
import com.ix.cookbook.screens.recipes.defaultMealTypeFilters
import com.ix.cookbook.ui.theme.CookbookTheme
import com.ix.cookbook.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    mealFilters: List<MealTypeFilter>,
    dietFilters: List<DietTypeFilter>,
    activeMealType: MealTypeFilter?,
    activeDietType: DietTypeFilter?,
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    onSubmit: (mealType: MealTypeFilter?, dietType: DietTypeFilter?) -> Unit,
) {
    var selectedMealType: MealTypeFilter? by remember { mutableStateOf(activeMealType) }
    var selectedDietType: DietTypeFilter? by remember { mutableStateOf(activeDietType) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = MaterialTheme.spacing.m)
                .padding(bottom = MaterialTheme.spacing.l)
                .padding(top = 0.dp),
        ) {
            Section(
                title = "Meal type",
                filters = mealFilters,
                selected = selectedMealType?.id,
                active = selectedMealType,
                onSelect = { filter -> selectedMealType = filter as MealTypeFilter? },
            )
            Section(
                title = "Diet type",
                filters = dietFilters,
                selected = selectedDietType?.id,
                active = selectedDietType,
                onSelect = { filter -> selectedDietType = filter as DietTypeFilter? },
            )
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = { onSubmit(selectedMealType, selectedDietType) },
            ) {
                Text("Apply")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Section(
    title: String,
    filters: List<Filter>,
    selected: String?,
    active: Filter?,
    onSelect: (Filter?) -> Unit,
) {
//    TODO: add animations
    Column {
        Row(
            modifier = Modifier
                .heightIn(min = FilterChipDefaults.Height + MaterialTheme.spacing.m),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.l))
            if (active != null) {
                FilterChip(
                    selected = true,
                    onClick = { onSelect(null) },
                    label = { Text(active.label) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.content_desc_checked),
                        )
                    },
                )
            }
        }
        Row(
            Modifier
                .horizontalScroll(rememberScrollState())
                .padding(end = MaterialTheme.spacing.m),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.s),
        ) {
            filters.forEach {
                if (it.id != selected) {
                    FilterChip(
                        selected = false,
                        label = { Text(it.label) },
                        onClick = {
                            onSelect(it)
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BottomSheetPreview() {
    val sheetState = rememberModalBottomSheetState()

    CookbookTheme {
        BottomSheet(
            mealFilters = defaultMealTypeFilters,
            dietFilters = defaultDietTypeFilters,
            activeMealType = null,
            activeDietType = null,
            sheetState = sheetState,
            onDismissRequest = {},
            onSubmit = { _, _ -> },
        )
    }
}
