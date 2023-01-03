import React from "react";
import ChangePasswordModal from "./ChangePasswordModal";
import { render, fireEvent } from "@testing-library/react";test("check whether submit button closes popup", () => {
  const handleClose = jest.fn();
  const { getByText } = render(
    <ChangePasswordModal onClose={handleClose}>
      <div>Change Password</div>
    </ChangePasswordModal>
  );
  expect(getByText("Change Password")).toBeTruthy();
  fireEvent.click(getByText(/Submit/i));
});