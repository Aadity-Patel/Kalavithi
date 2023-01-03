import React, { useState } from "react";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";
import EmailIcon from "@mui/icons-material/Email";
import { Alert } from "@mui/material";
import "./LoginModal.css";
import BaseAPI from "../API";

function LoginModal({ openState: openLoginState, setOpenState, setLoggedIn }) {
  const [email, setEmail] = useState("");
  const [emailError, setEmailError] = useState("");
  const [password, setPassword] = useState("");
  const [passwordError, setPasswordError] = useState("");
  const [loginError, setLoginError] = useState(false);
  const [loginSuccessful, setLoginSuccessful] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const handleClickShowPassword = () => setShowPassword(!showPassword);
  const handleClose = () => {
    setEmail("");
    setEmailError("");
    setPassword("");
    setPasswordError("");
    setLoginError(false);
    setOpenState(false);
  };
  const isValidCredentials = (response) => {
    localStorage.setItem("is-logged-in", true);
    localStorage.setItem("username", response.data.username);
    localStorage.setItem("userId", response.data.id);
  };

  const handleLoginPopUpClose = () => {
    setTimeout(
      function () {
        handleClose();
      }.bind(this),
      2000
    );
  };

  const handleEmailValidation = (event) => {
    if (!event.target.value.match(/^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/)) {
      setEmail(event.target.value);
      setEmailError("Invalid email");
    } else {
      setEmail(event.target.value);
      setEmailError("");
    }
  };

  const handlePasswordValidation = (event) => {
    if (
      !event.target.value.match(
        /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$/
      )
    ) {
      setPassword(event.target.value);
      setPasswordError("Incorrect Credentials");
    } else {
      setPassword(event.target.value);
      setPasswordError("");
    }
  };

  async function handleSubmit(event) {
    event.preventDefault();

    try {
      let response = await BaseAPI.loginURL(email, password);
      isValidCredentials(response);
      setLoginSuccessful(true);
      setLoggedIn(true);
      handleLoginPopUpClose();
      setLoginError(false);
    } catch (error) {
      setLoginError(true);
    }
  }

  return (
    <div>
      <Modal keepMounted open={openLoginState} onClose={handleClose}>
        <Box
          className="login-modal-box"
          sx={{
            "& .MuiTextField-root": { m: 1, width: "25ch" },
          }}
        >
          <div id="login-modal-title">
            <br></br>
            <h1>
              <b>User Login</b>
            </h1>
            <br></br>
          </div>
          <div className="modal-body">
            <TextField
              label="Email"
              spacing={2}
              value={email}
              style={{ width: "80%" }}
              helperText={emailError}
              error={emailError}
              InputLabelProps={{ style: { fontSize: 15 } }}
              InputProps={{
                style: { fontSize: 15 },
                startAdornment: (
                  <InputAdornment position="start">
                    <EmailIcon />
                  </InputAdornment>
                ),
              }}
              onChange={(e) => {
                setEmail(e.target.value)
                handleEmailValidation(e) 
              }}
            />
            <TextField
              label="Password"
              type={showPassword ? "text" : "password"}
              style={{ width: "80%" }}
              value={password}
              error={passwordError}
             // helperText={passwordError}
              InputLabelProps={{ style: { fontSize: 15 } }}
              InputProps={{
                style: { fontSize: 15 },
                startAdornment: (
                  <InputAdornment position="start">
                    <IconButton onClick={handleClickShowPassword}>
                      {showPassword ? <Visibility /> : <VisibilityOff />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
              onChange={(e) => {
                setPassword(e.target.value);
                handlePasswordValidation(e);
              }}
            />
          </div>
          {loginError === true ? (
            <Alert severity="warning">Invalid Credentials</Alert>
          ) : (
            <div></div>
          )}
          {loginSuccessful === true ? (
            <Alert severity="success">LoggedIn Successfully!</Alert>
          ) : (
            <div></div>
          )}
          <br></br>
          <div className="modal-footer">
            <div className="col text-center">
              <Button
                type="submit"
                variant="contained"
                size="medium"
                className="login-modal-button"
                disabled={email === "" || password === "" ? true : false}
                onClick={handleSubmit}
              >
                Submit
              </Button>
              <Button
                variant="contained"
                size="medium"
                color="error"
                className="login-modal-button"
                buttonText="Cancel"
                onClick={() => {
                  handleClose();
                }}
              >
                Cancel
              </Button>
            </div>
          </div>
        </Box>
      </Modal>
    </div>
  );
}

export default LoginModal;
