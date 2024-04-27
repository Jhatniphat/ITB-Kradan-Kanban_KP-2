export async function getAllTasks() {
  try {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks` , { method : "GET" }); //GET Method
    const items = await res.json();
    return items;
  } catch (error) {
  }
}

export async function getTaskById(id) {
  let res, item;
  try {
    res = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks/${id}` , { method : "GET" });
    if (res.status === 200) {
    item = await res.json();
    item.createdOn = timeFormater(item.createdOn);
    item.updatedOn = timeFormater(item.updatedOn);
    return item;
  }
  else { return res.status }
  } catch (error) {
    return error;
  }
}

function timeFormater(time) {
  return new Date(time).toLocaleString("th-TH", { timeZone: Intl.DateTimeFormat().resolvedOptions().timeZone });
}



