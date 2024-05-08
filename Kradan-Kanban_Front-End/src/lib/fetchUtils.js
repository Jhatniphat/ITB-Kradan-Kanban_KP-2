// ! -------------------------------- Task ------------------------------------------
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
  // console.log(JSON.stringify({ ...newTask }));
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
      return item;
    } else {
      return res.status;
    }
  } catch (error) {
    return error;
  }
}

export async function editTask(id, Task) {
  Task.status = titleCaseToENUM(Task.status);
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
      item.status = ENUMToTitleCase(item.status);
      return item;
    } else {
      return res.status;
    }
  } catch (error) {
    return error;
  }
}

// ! ------------------------------- Status --------------------------------
export async function getAllStatus() {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/statuses`, {
      method: "GET",
    }); //GET Method
    const items = await res.json();
    return items;
  } catch (error) {}
}

export async function getStatusById(id) {
  let res, item;
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/statuses/${id}`, {
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

export async function addStatus(newStatus) {
  let res, item;
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/statuses`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ ...newStatus }),
    });
    if (res.status === 201) {
      item = await res.json();
      return item;
    } else {
      return res.status;
    }
  } catch (error) {
    return error;
  }
}

export async function editStatus(id, Task) {
  let res , item
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/statuses/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(Task),
    });

    if (res.ok) {
      let updatedStatus = await res.json();
      return updatedStatus;
    } else {
      throw new Error(`Failed to update status: ${res.status}`);
    }
  } catch (error) {
    throw new Error(`Error updating status: ${error.message}`);
  }
}

export async function deleteStatus(id) {
  let res, item;
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/statuses/${id}`, {
      method: "DELETE",
    });
    if (res.ok) {
      item = await res.json();
      item.createdOn = timeFormater(item.createdOn);
      item.updatedOn = timeFormater(item.updatedOn);
      return item;
    }else {
      return res.status
    }
  }catch (error) {
    return error;
  }
}

export async function transferStatus(oldId , newId) {
  let res, item;
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/statuses/${oldId}/${newId}`, {
      method : "DELETE"
    })
    if (res.ok) {
      item = await res.json();
      return item;
    }
    if (res.status === 400) {
      item = await res.json();
      return item;
    }
  } catch (err){
    return err;
  }
}

// ! -------------------------- Private function ----------------------------

function timeFormater(time) {
  return new Date(time).toLocaleString("en-GB", {
    timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone,
  });
}

function ENUMToTitleCase(str){
  if(str === null || str === '') return 'No Status' 
  // str ?? return 'No Status'
  const words = str.split("_");
  const titleCaseWords = words.map((word) => {
    return word[0].toUpperCase() + word.slice(1).toLowerCase();
  });
  return titleCaseWords.join(" ");
}

function titleCaseToENUM(str) {
  return str.split(" ").join("_").toUpperCase();
}