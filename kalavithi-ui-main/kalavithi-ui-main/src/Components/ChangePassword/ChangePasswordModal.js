import React, { useState } from "react";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import InputAdornment from "@mui/material/InputAdornment";
import IconButton from "@mui/material/IconButton";
import { Alert } from "@mui/material";
import BaseAPI from "../API";
import "./ChangePasswordModal.css";



function ChangePasswordModal({ openState: openLoginState, setOpenState }) {
  const [oldPassword, setoldPassword] = useState("");
  const [newPassword, setnewPassword] = useState("");
  const [passwordMatch, setpasswordMatch] = useState(false);
  const [passwordNotMatch, setpasswordNotMatch] = useState(false);
  const [ConfirmNewPassword, setConfirmNewPassword] = useState("");
  const [OldPasswordError, setOldPasswordError] = useState("");
  const [NewPasswordError, setNewPasswordError] = useState("");
  const [confirmNewPasswordError, setconfirmNewPasswordError] = useState("");
  const [showOldPassword, setShowOldPassword] = useState(false);
  const [showNewPassword, setShowNewPassword] = useState(false);
  const [showConfirmNewPassword, setShowConfirmNewPassword] = useState(false);
  const [passwordChangedSuccessfully, setpasswordChangedSuccessfully] = useState(false);
  const [passwordNotChanged, setpasswordNotChanged] = useState(false);

  const handleClickShowOldPassword = () => setShowOldPassword(!showOldPassword);
  const handleClickShowNewPassword = () => setShowNewPassword(!showNewPassword);
  const handleClickShowConfirmNewPassword = () =>
    setShowConfirmNewPassword(!showConfirmNewPassword);
  const handleClose = () => {
    setOldPasswordError("");
    setNewPasswordError("");
    setconfirmNewPasswordError("");
    setoldPassword("");
    setnewPassword("");
    setConfirmNewPassword("");
    setpasswordChangedSuccessfully(false);
    setpasswordNotChanged(false);
    setpasswordMatch(false);
    setpasswordNotMatch(false);
    setOpenState(false);
  };

  const handleChangePasswordPopUpClose = () => {
    setTimeout(
      function () {
        handleClose();
      }.bind(this),
      1000
    );
  };

  const email = localStorage.getItem("username");
  
  async function handleSubmit(event) {
    event.preventDefault();
    try{
      let response = await BaseAPI.changePasswordURL(email,oldPassword,newPassword)
      setpasswordMatch(false);
      setpasswordNotMatch(false);
      setpasswordChangedSuccessfully(true);
      handleChangePasswordPopUpClose();
    }catch(error){
      console.log(error);
      setpasswordMatch(false);
      setpasswordNotMatch(false);
      setpasswordNotChanged(true);
    }     
  }
  const ifBothNewPasswordIsSameAsConfirmPassword = (event) => {
    if (event.target.value !== newPassword) {
      setpasswordNotMatch(true);
      setpasswordMatch(false);
    } else {
      setpasswordNotMatch(false);
      setpasswordMatch(true);
    }
  }

  // const compareBothPassword = ()=>{
  //   if(newPassword!=ConfirmNewPassword){
  //     setpasswordMatch(false)
  //     setpasswordNotMatch(false)
  //   }else{
  //     setpasswordMatch(true)
  //   }
  // }


  const handleOldPasswordValidation = (event) => {
    if (
      !event.target.value.match(
        /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$/
      )
    ) {
      setoldPassword(event.target.value);
      setOldPasswordError("Incorrect Credentials");
    } else {
      setoldPassword(event.target.value);
      setOldPasswordError("");
    }
  };

  const handleNewPasswordValidation = (event) => {
    if (
      !event.target.value.match(
        /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$/
      )
    ) {
      setnewPassword(event.target.value);
      setNewPasswordError("Incorrect Credentials");
    } else {
      setnewPassword(event.target.value);
      setNewPasswordError("");
    }
  };
  const handleConfirmNewPasswordValidation = (event) => {
    if (
      !event.target.value.match(
        /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$/
      )
    ) {
      setConfirmNewPassword(event.target.value);
      setconfirmNewPasswordError("Incorrect Credentials");
    } else {
      setConfirmNewPassword(event.target.value);
      setconfirmNewPasswordError("");
    }
  };

  return (
    <div>
      <Modal keepMounted open={openLoginState} onClose={handleClose}>
        <Box
          className="change-password-modal-box"
          sx={{
            "& .MuiTextField-root": { m: 1, width: "25ch" },
          }}
        >
          <Typography
            id="change-password-modal-title"
            variant="h6"
            component="h2"
           
          >
            <br></br>
            <h1><b>Change Password</b></h1>
            <br></br>
          </Typography>
          <div className="modal-body">
            <TextField
              label="Old Password"
              value={oldPassword}
              type={showOldPassword ? "text" : "password"}
              error={OldPasswordError}
              helperText={OldPasswordError}
              style={{ width: "80%" }}
              InputLabelProps={{ style: { fontSize: 15 } }}
              InputProps={
                { style: { fontSize: 15 } ,
                
                  startAdornment: (
                    <InputAdornment position="start">
                      <IconButton onClick={handleClickShowOldPassword}>
                        {showOldPassword ? <Visibility /> : <VisibilityOff />}
                      </IconButton>
                    </InputAdornment>
                  ),
                  }
              }
              onChange={(e) => {
                handleOldPasswordValidation(e);
                setoldPassword(e.target.value);
              }}
            />

            <TextField
              label="New Password"
              value={newPassword}
              type={showNewPassword ? "text" : "password"}
              error={NewPasswordError}
              helperText={NewPasswordError}
              style={{ width: "80%" }}
              InputLabelProps={{ style: { fontSize: 15 } }}
              InputProps={
                { style: { fontSize: 15 } ,
                
                  startAdornment: (
                    <InputAdornment position="start">
                      <IconButton onClick={handleClickShowNewPassword}>
                        {showNewPassword ? <Visibility /> : <VisibilityOff />}
                      </IconButton>
                    </InputAdornment>
                  ),
                  }
              }
              onChange={(e) => {
                handleNewPasswordValidation(e);
                setnewPassword(e.target.value);
              }}
            />

            <TextField
              label="Confirm New Password"
              value={ConfirmNewPassword}
              type={showConfirmNewPassword ? "text" : "password"}
              style={{ width: "80%" }}
              error={confirmNewPasswordError}
              helperText={confirmNewPasswordError}
              InputLabelProps={{ style: { fontSize: 15} }}
              InputProps={{style:{fontSize: 15} ,
                
                  startAdornment: (
                    <InputAdornment position="start">
                      <IconButton onClick={handleClickShowConfirmNewPassword}>
                        {showConfirmNewPassword ? (
                          <Visibility />
                        ) : (
                          <VisibilityOff />
                        )}
                      </IconButton>
                    </InputAdornment>
                  ),
                
              }
            }
              onChange={(e) => {
                setConfirmNewPassword(e.target.value);
                handleConfirmNewPasswordValidation(e);
                ifBothNewPasswordIsSameAsConfirmPassword(e);
              }}
            />
          </div>

          {passwordNotMatch === true ? (
            <Alert severity="warning"> Password does not match</Alert>
          ) : (
            <div></div>
          )}

          {passwordMatch === true ? (
            <Alert severity="success"> Password Match</Alert>
          ) : (
            <div></div>
          )}

          {passwordChangedSuccessfully === true ? (
            <Alert severity="success">Password Changed</Alert>
          ) : (
            <div></div>
          )}

          {passwordNotChanged === true ? (
            <Alert severity="warning">Password Not Changed</Alert>
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
              disabled={
                oldPassword === "" ||
                newPassword === "" ||
                ConfirmNewPassword === "" ||
                passwordMatch === false
                  ? true
                  : false 
                 
              }
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
              onClick={handleClose}
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

export default ChangePasswordModal;
