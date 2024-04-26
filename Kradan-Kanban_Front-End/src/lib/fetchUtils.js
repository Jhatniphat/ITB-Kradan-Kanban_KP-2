async function getAllTasks() {
    try {
      const data = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks`) //GET Method
      const items = await data.json()
      return items
    } catch (error) {
      console.log(`error: ${error}`)
    }
  }
  async function getTaskById(id) {
    let data , item
    try {
      data = await fetch(`${import.meta.env.VITE_BASE_URL}/tasks/${id}`)
      item = await data.json()
      return item
    } catch (error) {
      console.log('Member Not Found!')
      console.log(`error: ${error}`)
      console.log(data.status)
      if (data.status === 404) return undefined
    }
  }
