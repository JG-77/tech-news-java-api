 // JavaScript and CSS files need to be placed first in /resources/static, then in their own js or css folders
//declare the upvoteClickHandler() as an async JavaScript function
 async function upvoteClickHandler(event) {
   event.preventDefault();
   //parse out the id via the window.location() method
   const id = window.location.toString().split('/')[
     window.location.toString().split('/').length - 1
   ];

//By awaiting the response (response = await), we can capture the id value of the post and save it to a property (postId)
//when the fetch() method makes a call to the /posts/upvote route
//we capture the id of the location and then send it as a PUT request
   const response = await fetch('/posts/upvote', {
     method: 'PUT',
     body: JSON.stringify({
         postId: id
     }),
     headers: {
       'Content-Type': 'application/json'
     }
   });

   if (response.ok) {
     document.location.reload();
   } else {
     alert(response.statusText);
   }
 }
//document.querySelector() attaches an event listener to the button, using class upvote-btn, and awaits a click
 document.querySelector('.upvote-btn').addEventListener('click', upvoteClickHandler);