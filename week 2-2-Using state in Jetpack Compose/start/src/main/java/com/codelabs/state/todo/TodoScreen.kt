/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelabs.state.todo

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelabs.state.util.generateRandomTodoItem
import kotlin.random.Random

/**
 * Stateless component that is responsible for the entire todo screen.
 *
 * @param items (state) list of [TodoItem] to display
 * @param onAddItem (event) request an item be added
 * @param onRemoveItem (event) request an item be removed
 */
@Composable
fun TodoScreen(
    items: List<TodoItem>,
    currentlyEditing: TodoItem?,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit,
    onStartEdit: (TodoItem) -> Unit,
    onEditItemChange: (TodoItem) -> Unit,
    onEditDone: () -> Unit
) {
    Column {
        /// current item이 없으면 올라오고
        /// current item이 있으면 내려감
        val enableTopSection = currentlyEditing == null
        TodoItemInputBackground(elevate = enableTopSection) {
            if(enableTopSection) {
                TodoItemEntryInput(onItemComplete = onAddItem)
            } else {
                Text(
                    "Editing item",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(16.dp)
                        .fillMaxWidth()
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)
        ) {
            items(items) { todo ->
                if(currentlyEditing?.id == todo.id) { /// 수정모드일 경우,
                    /// lineeditor 선택 아이템 수정 모드
                    TodoItemInlineEditor(
                        item = currentlyEditing,
                        onEditItemChange = onEditItemChange,
                        onEditDone = onEditDone,
                        onRemoveItem = { onRemoveItem(todo) }
                    ) 
                } else {
                    TodoRow(
                        todo = todo,
                        { onStartEdit(it) },
                        modifier = Modifier.fillParentMaxWidth()
                    )
                }
            }
        }
    }
}

/**
 * Stateless composable that displays a full-width [TodoItem].
 *
 * @param todo item to show
 * @param onItemClicked (event) notify caller that the row was clicked
 * @param modifier modifier for this element
 */
@Composable
fun TodoRow(
    todo: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier,
    iconAlpha : Float = remember(todo.id) { randomTint() }
) {
    Row(
        modifier = modifier
            .clickable { onItemClicked(todo) }
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(todo.task)
        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = todo.icon.contentDescription)
        )
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}

@Composable
fun TodoInputTextField(text: String, onTextChange: (String) -> Unit, modifier: Modifier){
    Log.d("dev", "TodoInputTextField")
    TodoInputText(text, onTextChange, modifier)
}

@Composable
fun TodoItemEntryInput (onItemComplete: (TodoItem) -> Unit) {
    val (text, onTextChange) = remember { mutableStateOf("") }
    val (icon, onIconChange) = remember { mutableStateOf(TodoIcon.Default) }
    val submit = {
        if(text.isNotBlank()){
            onItemComplete(TodoItem(text, icon))
            onTextChange("")
            onIconChange(TodoIcon.Default)
        }
    }
    TodoItemInput(
        text = text,
        onTextChange = onTextChange,
        icon = icon,
        onIconChange = onIconChange,
        submit = submit,
        iconsVisible = text.isNotBlank()
    ) { // @Composable buttonSlot을 채움.
        TodoEditButton(
            onClick = submit,
            text = "Add",
            enabled = text.isNotBlank()
        )
    }
}

@Composable
fun TodoItemInlineEditor(
  item: TodoItem,
  onEditItemChange: (TodoItem) -> Unit,
  onEditDone: () -> Unit,
  onRemoveItem: () -> Unit
) = TodoItemInput(
    text = item.task,
    onTextChange = { onEditItemChange(item.copy(task = it)) }, // item.task 라서 task = it
    icon = item.icon,
    onIconChange = { onEditItemChange(item.copy(icon = it)) },  // item.icon 이라서 icon = it
    submit = onEditDone,
    iconsVisible = true,
    buttonSlot = {
        Row {
            val shrinkButtons = Modifier.widthIn(20.dp)
            TextButton(onClick = onEditDone, modifier = shrinkButtons){
                Text(
                    text = "\uD83D\uDCBE", // floppy disk
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(30.dp)
                )
            }
            TextButton(onClick = onRemoveItem, modifier = shrinkButtons){
                Text(
                    text = "❌",
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(30.dp)
                )
            }
        }
    }
) // inline 함수 사용시, 아래 함수가 있어야 함.


@Composable
fun TodoItemInput(
    text: String,
    onTextChange: (String) -> Unit,
    icon: TodoIcon,
    onIconChange: (TodoIcon) -> Unit,
    submit: () -> Unit,
    iconsVisible: Boolean,
    buttonSlot: @Composable () -> Unit
) {
    Column {
        Row(
            Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Log.d("dev", "TodoItemInput")
            TodoInputText(
                text,
                onTextChange = onTextChange,
                Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                onImeAction = submit
            )

            Spacer(modifier = Modifier.width(8.dp))
            Box(Modifier.align(Alignment.CenterVertically)) { buttonSlot() }
        }
        if (iconsVisible) {
            AnimatedIconRow(icon = icon, onIconChange = onIconChange, Modifier.padding(top = 8.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }


    }
}

@Preview
@Composable
fun PreviewTodoItemInput() = TodoItemEntryInput(onItemComplete = {})

@Preview
@Composable
fun PreviewTodoScreen() {
    val items = listOf(
        TodoItem("Learn compose", TodoIcon.Event),
        TodoItem("Take the codelab"),
        TodoItem("Apply state", TodoIcon.Done),
        TodoItem("Build dynamic UIs", TodoIcon.Square)
    )
    TodoScreen(items, null, {}, {}, {}, {}, {})
}

@Preview
@Composable
fun PreviewTodoRow() {
    val todo = remember { generateRandomTodoItem() }
    TodoRow(todo = todo, onItemClicked = {}, modifier = Modifier.fillMaxWidth())
}
