// * Example
// ? console.log(await getAllTasks())
// ? console.log(await addTask( { title : "blaabla" } ))
// ? console.log(await getTaskById())
// ? console.log(await editTask(1 , {title : "Get World!!"}))
// ? console.log(await deleteTask(1))

export async function getAllTasks() {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks`, {
      method: "GET",
    }); //GET Method
    const items = await res.json();
    return items;
  } catch (error) {}
}

export async function getTaskById(id) {
  let res, item;
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks/${id}`, {
      method: "GET",
    });
    if (res.status === 200) {
      item = await res.json();
      item.createdOn = timeFormater(item.createdOn);
      item.updatedOn = timeFormater(item.updatedOn);
      return item;
    } else {
      return res.status;
    }
  } catch (error) {
    return error;
  }
}

export async function addTask(newTask) {
  let res, item;
  console.log(JSON.stringify({ ...newTask }));
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ ...newTask }),
    });
    if (res.status === 201) {
      item = await res.json();
      console.log(res.status);
      return item;
    } else {
      return res.status;
    }
  } catch (error) {
    return error;
  }
}

// export async function editTask(id, Task) {
//   let res, item;
//   try {
//     res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks/${id}`, {
//       method: "PUT",
//       headers: {
//         "Content-Type": "application/json",
//       },
//       body: JSON.stringify({ ...Task }),
//     });
//     if (res.status === 200) {
//       item = await res.json();
//       return item;
//     } else {
//       return res.status;
//     }
//   } catch (error) {
//     return error;
//   }
// }

export async function editTask(id, Task) {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(Task),
    });

    if (res.ok) {
      const updatedTask = await res.json();
      return updatedTask;
    } else {
      throw new Error(`Failed to update task: ${res.status}`);
    }
  } catch (error) {
    throw new Error(`Error updating task: ${error.message}`);
  }
}

export async function deleteTask(id) {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks/${id}`, {
      method: "DELETE",
    });
    if (res.ok) {
      const item = await res.json();
      console.log(res.status);
      return item;
    } else {
      return res.status;
    }
  } catch (error) {
    return error;
  }
}

// export async function deleteTask(id) {
//   let res, item;
//   try {
//     (res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks/${id}`)),
//       { method: "DELETE" };
//     if (res.status === 200) {
//       item = await res.json();
//       return item;
//     } else {
//       return res.status;
//     }
//   } catch (error) {
//     return error;
//   }
// }

function timeFormater(time) {
  return new Date(time).toLocaleString("en-GB", {
    timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone,
  });
}
