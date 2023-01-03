import {
    render,
   
    screen,
   
    waitFor,
  } from "@testing-library/react";
  import "@testing-library/jest-dom";
  import React from "react";
  import KalaList from "../KalaList";
  import LikeImage from "./LikeImage";

  import userEvent from '@testing-library/user-event';

  describe('LikeImage', ()=>{
    test('Like button should be visible on hover', ()=>{
        async () => {
        render(<KalaList/>)
        
        let triggerLike = await waitFor(()=> screen.queryByTestId("like-icon"))
        expect(triggerLike).not.toBeVisible()

        const trigger = screen.getByTestId("image-dialog");
        
        userEvent.hover(trigger)
        expect(triggerLike).toBeVisible()
        
        await waitFor(()=> userEvent.unhover(trigger))
        expect(triggerLike).not.toBeVisible()

      }
    })

    test('Unfilled like icon should be changed to Filled like icon when clicked', ()=>{
        async ()=>{
        render(<LikeImage/>)

        let triggerUnlike =await waitFor(()=> screen.getByTestId('unfilled-like-icon'))
        
        fireEvent.click(triggerUnlike)

        let triggerLike = await waitFor(()=> screen.getByTestId('filled-like-icon'))
        expect(triggerLike).toBeVisible()
        expect(triggerUnlike).not.toBeVisible()
        }
    })

    
    test('Filled like icon should be changed to Unfilled like icon when clicked', ()=>{
        async ()=>{
        render(<LikeImage/>)

        let triggerUnlike =await waitFor(()=> screen.getByTestId('unfilled-like-icon'))
        fireEvent.click(triggerUnlike)

        let triggerLike =await waitFor(()=> screen.getByTestId('filled-like-icon'))
        expect(triggerLike).toBeVisible()

        fireEvent.click(triggerLike)
        expect(triggerUnlike).toBeVisible()
        expect(triggerLike).not.toBeVisible()
        }
    })

  })
  