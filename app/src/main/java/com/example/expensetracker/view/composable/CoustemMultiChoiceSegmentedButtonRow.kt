package com.example.expensetracker.view.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import com.example.expensetracker.domain.model.ExpenseCategory


@Composable
fun CoustemMultiChoiceSegmentedButtonRow(
    selectedCategory: ExpenseCategory,
    onCategorySelected: (ExpenseCategory)-> Unit,
    categoryList : List<ExpenseCategory> =  ExpenseCategory.entries

){
    MultiChoiceSegmentedButtonRow {
        categoryList.forEach{ label ->
            SegmentedButton (
                checked =  selectedCategory == label,
                onCheckedChange = { onCategorySelected(label) },
                colors = SegmentedButtonDefaults.colors(
                    activeBorderColor = Color(0xFF9C27B0),
                    activeContentColor = Color(0xffffffff),
                    activeContainerColor = Color(0xFF44BE49)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                shape = RoundedCornerShape(10.dp),
//                contentPadding = PaddingValues(horizontal = 10.dp)
            ) {
                Text("$label".lowercase(),
                    fontSize = 18.sp,
                    )
            }

        }
    }
}







@Composable
fun SingleChoiceWrapFilter(
    selectedCategory: ExpenseCategory,
    onCategorySelected: (ExpenseCategory)-> Unit,
    categoryList : List<ExpenseCategory> =  ExpenseCategory.entries) {

    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp),
    ) {
        categoryList.forEach { option ->
            FilterChip(
                selected = selectedCategory == option,
                onClick = { onCategorySelected(option) },
                label = { Text(text = option.name, color = Color(0xffE5E7EB)) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFF44BE49)
                ),
                modifier = Modifier
                    .padding(start = 5.dp),
                shape = RoundedCornerShape(10.dp),            )
        }
    }
}
