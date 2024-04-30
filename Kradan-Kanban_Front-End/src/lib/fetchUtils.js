export async function getAllTasks() {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks`, {
      method: "GET",
    }) //GET Method
    const items = await res.json()
    return items
  } catch (error) {}
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
