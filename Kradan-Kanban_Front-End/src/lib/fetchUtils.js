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
    }) //GET Method
    const items = await res.json()
    return items
  } catch (error) { }
}

export async function getTaskById(id) {
  let res, item
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks/${id}`, {
      method: "GET",
    })
    if (res.status === 200) {
      item = await res.json()
      item.createdOn = timeFormater(item.createdOn)
      item.updatedOn = timeFormater(item.updatedOn)
      return item
    } else {
      return res.status
    }
  } catch (error) {
    return error
  }
}

export async function addTask(newTask) {
  let res, item
  console.log(JSON.stringify({...newTask}))
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({...newTask})
    })
    if (res.status === 201) {
      item = await res.json()
      return item
    } else {
      return res.status
    }
  } catch (error) {
    return error
  }
}

export async function editTask(id , Task) {
  let res , item
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks/${id}` , {
      method : "PUT",
            headers: {
        "Content-Type": "application/json",
      },
      body : JSON.stringify({...Task})
    })
    if (res.status === 200){
      item = await res.json()
      return item
    } else {
      return res.status
    }
  } catch (error) {
    return error
  }
}

export async function deleteTask(id) {
  let res , item
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks/${id}`) , { method : "DELETE" }
    if (res.status === 200) {
      item = await res.json()
      return item
    } else {
      return res.status
    }
  } catch (error) {
    return error
  }
}

function timeFormater(time) {
  return new Date(time).toLocaleString("en-GB", {
    timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone,
  })
}


// function timeFormater(time) {
//   const options = {
//     timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone,
//     hour12: false, // Use 24-hour format
//     hour: "numeric",
//     minute: "numeric",
//     second: "numeric",
//     year: "numeric",
//     month: "2-digit",
//     day: "2-digit",
//   }

//   const dateOptions = {
//     timeZone: "UTC",
//     year: "numeric",
//     month: "2-digit",
//     day: "2-digit",
//   }

//   const thaiLocale = "th-TH"

//   const thaiTimeString = new Date(time).toLocaleString(thaiLocale, options)
//   const enDateString = new Date(time).toLocaleString("en-GB", dateOptions)

//   const thaiTime = thaiTimeString.split(", ")
//   const [enDay, enMonth, enYear] = enDateString.split("/")

//   return `${enDay}/${enMonth}/${enYear} ${thaiTime}`
// }
