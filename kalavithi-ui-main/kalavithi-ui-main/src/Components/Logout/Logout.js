import BaseAPI from "../API";

export default function logout() {
  localStorage.clear();
  try{
    let response = await BaseAPI.logoutURL()
  }catch(error){
    console.log(error)
  }
 
  window.location.href = "/";
}
