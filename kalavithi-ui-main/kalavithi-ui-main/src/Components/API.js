import axios from "axios";

const BaseAPI=axios.create({
    baseURL : "https://kalavithi-team2-service.herokuapp.com/api"
  
})
var token = "";
const imagesURL = async () => {
  const response = await BaseAPI.get("/images");
  return response;
};

const registerURL = async (mobileNumber, email, password) => {
  const response = await BaseAPI.post("/users/register", {
    mobileNumber: mobileNumber,
    email: email,
    password: btoa(password),
  });
  return response;
};

const likeImageURL = async (userId, imageId) => {
  const response = await BaseAPI.post("/users/like", {
    userId: userId,
    imageId: imageId,
  },
  {
    headers:{Authorization: localStorage.getItem('AUTH_TOKEN')}
  });
  return response;
};

const likeColourURL = async (userId) => {
  const response = await BaseAPI.get("/users/like-image/" + userId, {
    headers: { Authorization: localStorage.getItem('AUTH_TOKEN')}
  });
  return response;
};
const likeCountURL = async () => {
  const response = await BaseAPI.get("/users/image-like-count/");
  return response;
};

const loginURL = async (email, password) => {
  const response = await BaseAPI.get("/users/login", {
    headers: { Authorization: "Basic " + btoa(email + ":" + btoa(password)) },
  });
  token =   "Basic " + btoa(email + ":" + btoa(password));
  localStorage.setItem('AUTH_TOKEN', token);
  return response;
};

const logoutURL = async () => {
  const response = await BaseAPI.get("/users/logout");
  return response;
};

const changePasswordURL = async (email, oldPassword, newPassword) => {
  const response = await BaseAPI.post(
    "/users/change-password",
    {
      username: email,
      oldPassword: btoa(oldPassword),
      newPassword: btoa(newPassword),
    },
    {
      headers: {
        Authorization: "Basic " + btoa(email + ":" + btoa(oldPassword)),
      },
    }
  );
  token =   "Basic " + btoa(email + ":" + btoa(newPassword));
  localStorage.setItem('AUTH_TOKEN', token);
  return response;
};

export default {
  imagesURL,
  registerURL,
  loginURL,
  logoutURL,
  changePasswordURL,
  likeImageURL,
  likeColourURL,
  likeCountURL,
};
