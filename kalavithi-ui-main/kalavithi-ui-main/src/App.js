import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import KalaList from "./Components/KalaList";
import KalaListHeading from "./Components/KalaListHeading";
import SearchBox from "./Components/SearchBox";
import LandingButton from "./Components/Button/Button";
import LoginModal from "./Components/LoginModal/LoginModal";
import UserState from "./Components/UserState";
import RegistrationModal from "./Components/RegistrationModal/Registration";
import SettingsDropDown from "./Components/Button/SettingsDropDown";
import API from "./Components/API";

const App = () => {
  const [kalas, setKalas] = useState([]);
  const [openButtonPopUp, setOpenButtonPopUp] = useState(false);
  const [register, setRegister] = useState(false);
  const [loggedIn, setLoggedIn] = useState(false);
  const getImageRequest = async () => {
    const response = await API.imagesURL();
    setKalas(response.data.images);
  };

  useEffect(() => {
    getImageRequest();
  }, []);
  return (
    <div className="container-fluid kala-app">
      <div className="row align-items-center mt-4 mb-4 header-content">
        <KalaListHeading heading="Kalavithi" />
        <SearchBox />
        {UserState() === true ? (
          <div className="col d-flex justify-content-end flex-row pt-4">
            <LandingButton
              buttonText="Login"
              onClick={() => setOpenButtonPopUp(true)}
            />
            <LandingButton
              buttonText="Register"
              onClick={() => {
                setRegister(true);
              }}
            />
          </div>
        ) : (
          <div className="col d-flex justify-content-left flex-row">
            <div className="username col d-flex justify-content-end flex-row">{localStorage.getItem("username")}</div>
            <div className="justify-content-end flex-row">
              <SettingsDropDown setLoggedIn={setLoggedIn} />
            </div>
          </div>
        )}
      </div>
      <div className="container">
        <div className="row">
          <KalaList kalas={kalas} loggedIn={loggedIn}/>
        </div>
      </div>
      <LoginModal
        openState={openButtonPopUp}
        setOpenState={setOpenButtonPopUp}
        setLoggedIn={setLoggedIn}
      />

      <RegistrationModal openState={register} setOpenState={setRegister} />
    </div>
  );
};

export default App;
