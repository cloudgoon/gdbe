GDBE uses a well defined MVC design for simplicity. The following diagram displays the relevant GDBE components and how they interact with each other.

<img src='http://gdbe.googlecode.com/svn/trunk/documentation/images/gdbe-structure.png' border='1px' />

The application is broken down into distinct client (GWT) and server (AppEngine) components. The server component exposes a subset of the GData functionality to the client and is responsible for retrieving and storing authentication tokens.

## Authentication ##
Authentication makes use of the AppEngine user service to Google-authenticate users. Once the user is authenticated, a GData token is obtained via the GData AuthSub mechanism which will present the user with an approve/deny access control page. Tokens obtained in this way are kept in the AppEngine data store for later use. AuthSub session tokens don't have a default expiration date, they are revoked through the GData library.

The sign-out mechanism therefore first revokes the AuthSub session token and then signs the user out via the AppEngine User service.

## Client/Server RPC Service ##
Most of the action happens on the server through a GWT RPC service. The RPC service exposes functionality for retrieving documents and document contents as well as creating, updating and deleting documents. The GWT RPC capabilities make calling these server-side functions a simple matter.

## Client Structure ##
The client side is also structured to clearly separate the application logic from the user interface. The interface is encapsulated and broken down into Parts and Dialogs whose main purpose is to trigger Command events and display information back to the user.

Every feature in GDBE is associated with a Command event, and all are handled in a central place, known as the "controller" in MVC terms.

This Command driven implementation makes GDBE easily expanded to suit your needs.