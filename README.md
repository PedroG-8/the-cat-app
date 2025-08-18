TheCatApp

I started by configuring the project, the remote and local git repository as well as adding the main dependencies 
that were necessary (Coil, Koin, Retrofit), as well as increasing some of the versions of existent dependencies.

After that, I created the base project structure and started adding the necessary logic to implement the CatApi, pull
the images (kept the API key in the local.properties) and display a basic grid of cat images. This allow me to quickly 
understand how I would have to create my Models and Repos in order to easily implement the rest of the endpoint calls.

Later I added the rest of the API services and also implemented the local DB, Room the following way:

When launching the app or scrolling up to a point where I have no cats in the local DB:
Get 20 images of page X -> Get all favourites -> Store in room -> Fetch all cats from room -> Present in UI (through use of a Flow)

When adding/removing cat from favourites:
(API) Add/remove -> (Room) Update cat -> Present in UI (Flow updates automatically)

Searching:
Query in Room. Initially I wanted to use the API for searching but it does not return the cat image so is useless unless I already have the cat in the DB,
if that's the case, I rather just query it from Room anyway.

Detail:
Pass the cat imageId as argument through TypeSafe navigation and fetch the cat from Room through ID

Pagination:
I used DataStore to keep the last loaded page from the API. Everytime the user scrolls to the end of the List, I fetch the next page. This works
even if the user closes the app and opens again. I would prefer to use Pagination3 to handle it a bit cleaner but I couldn't find the time.

Unit Tests:
I built a few Unit tests for Repo, UseCase and ViewModel. I would like to create more to have full or almost full code coverage but unfortunately I didn't have
the time.

Notes:
I really enjoyed the challenge and hope I did well, I'm a bit frustrated that I could not finish everything as I would like, but I had to create
the entire project from scratch since I didn't have any template
