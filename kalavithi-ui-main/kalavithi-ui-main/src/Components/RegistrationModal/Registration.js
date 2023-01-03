import React, { useState} from 'react';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import "./Registration.css"
import { TextField } from '@mui/material';
import InputAdornment from "@mui/material/InputAdornment";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import IconButton from "@mui/material/IconButton";
import LocalPhoneIcon from '@mui/icons-material/LocalPhone';
import { Alert } from "@mui/material";
import EmailIcon from '@mui/icons-material/Email';
import BaseAPI from '../API';


function RegistrationModal({openState,setOpenState}){
  const [email,setEmail] = useState("");
  const [password,setPassword] = useState("");
  const [mobileNumber,setMoblieNumber]=useState("");
  const [emailError,setEmailError]=useState("");
  const [passwordError,setPasswordError]=useState("");
  const [mobileNumberError,setMobileNumberError]=useState("");
  const handleClickShowPassword = () => setShowPassword(!showPassword);
  const [showPassword, setShowPassword] = useState(false);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const CHARACTER_LIMIT = 10;
  const [registerSuccessful, setRegisterSuccessful] = useState(false);
  const [registrationError, setRegistrationError] = useState(false);
  const [alreadyRegistered,setAlreadyRegistered] = useState(false);

  const open = Boolean(anchorEl);
  const id = open ? 'simple-popover' : undefined;

  const handleClickPopover = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () =>{
      setEmailError("")
      setPasswordError("")
      setMobileNumberError("")
      setEmail("")
      setPassword("")
      setMoblieNumber("")
      setAlreadyRegistered(false)
      setRegisterSuccessful(false)
      setRegistrationError(false);
      setOpenState(false)}

  const handleRegisterPopUpClose = () => {
    setTimeout(
      function () {
        handleClose();
      }.bind(this),
      2000
    );
  };

  const handleMobileNumberValidation = (event) => {
      if (!event.target.value.match(/^[6-9]\d{9}$/)){
          setMoblieNumber(event.target.value)
          setMobileNumberError("Invalid Mobile Number");
      } else {
          setMoblieNumber(event.target.value)
          setMobileNumberError("");
      }
  };

  const handleEmailValidation = (event) => {
      if (!event.target.value.match(/^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/)) {
          setEmail(event.target.value)
          setEmailError("Invalid email");
      } else {
          setEmail(event.target.value)
          setEmailError("");
      }
  };

  const handlePasswordValidation = (event) => {
      if (!event.target.value.match(((/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,15}$/)))){
          setPassword(event.target.value)
          setPasswordError("Please enter a valid password");
      } else {
          setPassword(event.target.value)
          setPasswordError("");
      }
  };
  let handleSubmit = async (e) => {
      e.preventDefault();
      setPasswordError("");
    try {
        let res = await BaseAPI.registerURL(mobileNumber,email,password)

        if (res.status === 200) {
          setRegisterSuccessful(true);
          handleRegisterPopUpClose();
          setAlreadyRegistered(false);
      
        } 
      } 
        catch (err) {
          if(err.response.status===409){
            console.log(err);
            setAlreadyRegistered(true);
          }
          else{
            setRegistrationError(true);
          }
    }
    };
  
    return(
      <div>
          <Modal
              keepMounted
              open={openState}
              onClose={handleClose}
              data-testid="registration-pop-up"
          >
              <Box className="registration-modal-box">
                  <Typography id="registration-modal-title"
                    variant="h6" 
                  component="h2">
                      <br></br>
                      <h1><b>User Registration</b></h1>  
                  </Typography>
                  <div className = "modal-body">
                  <div className="mb-3 mt-4">
                      <TextField 
                      data-testid="mobile"
                      style={{ width: "80%" }}
                      helperText={mobileNumberError} error={mobileNumberError}
                      label ='Mobile Number' onChange={handleMobileNumberValidation}
                      value={mobileNumber} 
                      InputProps={{style:{fontSize:"15px"},
                      startAdornment: (
                            <InputAdornment position="start">
                              < LocalPhoneIcon/>
                            </InputAdornment>
                          )
                      }}
                      inputProps={{
                          maxlength: CHARACTER_LIMIT
                        }}
                      InputLabelProps={{style: {fontSize:"15px"}}}>
                      </TextField> 
                  </div>
                  <div className="mb-3 mt-4">
                      <TextField 
                      data-testid="email"
                      style={{ width: "80%" }}
                      helperText={emailError} error={emailError} 
                      label='Email' onChange={handleEmailValidation} 
                      value={email}
                      InputProps={{style: {fontSize:"15px"},
                          startAdornment: (
                            <InputAdornment position="start">
                              <EmailIcon/>
                            </InputAdornment>
                          )
                      }}            
                      InputLabelProps={{style: {fontSize:"15px"}}}>
                        </TextField>
                      </div>
                      <div className="mb-3">
                    <TextField 
                      data-testid="password"
                      style={{ width: "80%"}}
                      helperText={passwordError} 
                      error={passwordError} 
                      label='Password'
                      id="demo-helper-text-misaligned"
                      onChange={handlePasswordValidation}
                      value={password}
                      type={showPassword ? "text" : "password"}
                      InputProps={{style: {fontSize:"15px"},
                          startAdornment: (
                            <InputAdornment position="start">
                            <IconButton onClick={handleClickShowPassword}>
                          {showPassword ? <Visibility /> : <VisibilityOff />}
                            </IconButton>
                          </InputAdornment>
                          )
                        }}
                        aria-describedby={id}
                      onClick={handleClickPopover}
                      InputLabelProps={{style: {fontSize:"15px"}}}>
                      </TextField>
                      <br/>
                      <br/>
                      <br/>
                      <p style={{fontSize:"11px"}}>
                        
                        Password Requirement : At least 1 small letter, 1 capital letter, 1 special char, 1 number, 8-15 characters</p>
                      </div>
              </div>
              
            {registerSuccessful === true ? (
              <Alert severity="success">User Registered Successfully!</Alert>
          
            ) : (
              <div></div>
            )} 
            {alreadyRegistered === true ? (
              <Alert severity="warning">User Already Registered</Alert>
            ) : (
              <div></div>
            )}       
            {registrationError === true ? (
            <Alert severity="warning">Invalid Credentials</Alert>
          ) : (
            <div></div>
          )}   
              <div className="modal-footer">
              <div className="col text-center">
                  <Button variant="contained" data-testid="sign-up-button" size="large" className='registration-modal-button' value='Signup' 
                      disabled={(email === "" || password === "" || mobileNumber==="" ) ? true : false} onClick={handleSubmit}>Sign up</Button>
                  <Button variant="contained" size="large" color="error" className='registration-modal-button' data-testid="close-button" onClick={handleClose}>Close</Button>
              </div>
              </div>
              </Box>
          </Modal>
      </div>
  );
}

export default RegistrationModal;