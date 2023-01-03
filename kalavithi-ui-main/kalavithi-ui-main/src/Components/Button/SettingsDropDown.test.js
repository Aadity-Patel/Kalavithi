import SettingsDropDown from "./SettingsDropDown";
import {
  render,
  cleanup,
  fireEvent,
  screen,
  getByTestId,
  waitFor,
  getByRole,
  getByDisplayValue,
} from "@testing-library/react";
import "@testing-library/jest-dom";
import React from "react";
describe("SettingDropDown", () => {
  afterEach(cleanup);
  test("renders setting dropdown when setting icon clicked", () => {
    const { getByTestId } = render(<SettingsDropDown />);
    fireEvent.click(getByTestId("setting-icon"));
    expect(getByTestId("change-password")).toBeVisible();
    expect(getByTestId("logout")).toBeVisible();
  });
});