import Modal from "./Registration.js";
import React from "react";
import { render, fireEvent } from "@testing-library/react";
import {screen,getByTestId} from '@testing-library/dom'
import RegistrationModal from "./Registration.js";

test("check whether sign up button closes popup", () => {
  const handleClose = jest.fn();
  const { getByText } = render(
    <Modal onClose={handleClose}>
      <div>User Registration</div>
    </Modal>
  );
  expect(getByText("User Registration")).toBeTruthy();
  fireEvent.click(getByText(/Sign up/i));
});
test("should have the sign up button disabled when initialized" , () => {
        render(<RegistrationModal />);

        expect(screen.getByTestId('sign-up-button')).toBeInTheDocument();
        expect(screen.getByTestId('sign-up-button')).toBeDisabled();
    });
test("should have the close button enabled when initialized" , () => {
        render(<RegistrationModal />);

        expect(screen.getByTestId('close-button')).toBeInTheDocument();
        expect(screen.getByTestId('close-button')).toBeEnabled();
    });
test("should have the email textfield when initialized" , () => {
        render(<RegistrationModal />);

        expect(screen.getByTestId('email')).toBeInTheDocument();
    });
test("should have the mobile number textfield when initialized" , () => {
        render(<RegistrationModal />);

        expect(screen.getByTestId('mobile')).toBeInTheDocument();
    });
test("should have the password textfield when initialized" , () => {
        render(<RegistrationModal />);

        expect(screen.getByTestId('password')).toBeInTheDocument();
    });
test("User Registration pop up should open when registration button is clicked", () => {
        async () => {
          render(<RegistrationModal />);
          fireEvent.click(getByTestId("Register"));
          await waitFor(() => screen.getByTestId("registration-pop-up"));
          expect(getByTestId("registration-pop-up"));
        };
      });
  