import React from "react";
import Modal from "./LoginModal.js";
import { render, fireEvent } from "@testing-library/react";

test("check whether submit button closes popup", () => {
  const handleClose = jest.fn();
  const { getByText } = render(
    <Modal onClose={handleClose}>
      <div>User Login</div>
    </Modal>
  );
  expect(getByText("User Login")).toBeTruthy();
  fireEvent.click(getByText(/Submit/i));
});
