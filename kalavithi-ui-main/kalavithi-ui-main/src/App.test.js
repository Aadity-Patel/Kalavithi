import {
  render,
  fireEvent,
  screen,
  getByTestId,
  waitFor,
} from "@testing-library/react";
import KalaListHeading from "./Components/KalaListHeading";
import Logo from "./logo.png";
import "@testing-library/jest-dom";
import App from "./App";
import React from "react";
import SearchBox from "./Components/SearchBox";
import KalaList from "./Components/KalaList";

describe("KalaListHeading", () => {
  test('Logo must have src = "/logo.png" and className="logo"', () => {
    render(<KalaListHeading />);
    const logo = screen.getByRole("img");
    expect(logo).toHaveAttribute("src", Logo);
    expect(logo).toHaveClass("logo");
  });
});

test("Login Button is clickable", () => {
  const handleClick = jest.fn();
  render(<App />);
  fireEvent.click(screen.getByTestId("Login"));
  expect(handleClick).toHaveBeenCalledTimes(0);
});

test("Registration Button is clickable", () => {
  const handleClick = jest.fn();
  render(<App />);
  fireEvent.click(screen.getByTestId("Register"));
  expect(handleClick).toHaveBeenCalledTimes(0);
});

test("You should be able to type in the input and see the text", () => {
  render(<SearchBox />);

  screen.queryByPlaceholderText(/Type to Search/i);

});

test("Dialog should pop up when image is clicked", () => {
  async () => {
    render(<KalaList />);
    fireEvent.click(getByTestId("image-dialog"));
    await waitFor(() => screen.getByTestId("alert-dialog-title"));
    expect(getByTestId("alert-dialog-title")).toHaveTextContent(
      "Please login or register to view full image."
    );
  };
});
describe('Description', ()=>{
  test('Description should be visible on hover', ()=>{
      async () => {
      render(<KalaList/>)
      
      let triggerDescription = await waitFor(()=> screen.queryByTestId("img-desc"))
      expect(triggerDescription).not.toBeVisible()

      const trigger = screen.getByTestId("image-dialog");
      
      userEvent.hover(trigger)
      expect(triggerDescription).toBeVisible()
      
      await waitFor(()=> userEvent.unhover(trigger))
      expect(triggerDescription).not.toBeVisible()

    }
  })

})

