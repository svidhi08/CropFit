<!DOCTYPE html>

<html lang="en">

<head>

&nbsp;   <meta charset="UTF-8">

&nbsp;   <title>CropFit | Smart Agriculture App</title>

</head>

<body>



<h1 align="center">🌿 CropFit | Smart Agriculture App 🌾</h1>

<br/>



<h2 align="center">📌 Overview</h2>

<br/>



<p>

<b>CropFit</b> is an Android application that helps users choose the right crop based on soil and weather conditions.

The user provides values such as Nitrogen (N), Phosphorus (P), Potassium (K), temperature, humidity, pH, and rainfall.

Based on these inputs, the app predicts the most suitable crop using a machine learning model.

The application also stores previous predictions and maintains user profile details.

</p>



<hr/>



<h2 align="center">🔧 Tech Stack</h2>

<br/>



<table border="1" align="center" cellpadding="8" cellspacing="0">

&nbsp;   <tr>

&nbsp;       <th>Technology</th>

&nbsp;       <th>Usage</th>

&nbsp;   </tr>

&nbsp;   <tr>

&nbsp;       <td>Java</td>

&nbsp;       <td>Used for application logic and fragment handling</td>

&nbsp;   </tr>

&nbsp;   <tr>

&nbsp;       <td>Firebase Authentication</td>

&nbsp;       <td>User login and sign-up using email and password</td>

&nbsp;   </tr>

&nbsp;   <tr>

&nbsp;       <td>Firebase Firestore</td>

&nbsp;       <td>Stores user information and prediction history</td>

&nbsp;   </tr>

&nbsp;   <tr>

&nbsp;       <td>AI / ML Model</td>

&nbsp;       <td>Crop prediction logic implemented in CropModel.java</td>

&nbsp;   </tr>

&nbsp;   <tr>

&nbsp;       <td>View Binding</td>

&nbsp;       <td>Connects UI elements safely with Java code</td>

&nbsp;   </tr>

&nbsp;   <tr>

&nbsp;       <td>XML Layouts</td>

&nbsp;       <td>Used to design app screens with a clean interface</td>

&nbsp;   </tr>

</table>



<hr/>



<h2 align="center">📜 App Flow \& Architecture</h2>

<br/>



<p>

<b>1. Splash \& Authentication:</b>  

The app opens with a splash screen followed by login or sign-up using Firebase Authentication.

</p>



<p>

<b>2. Home Screen:</b>  

Users enter soil and weather values using sliders and input fields.

</p>



<p>

<b>3. Prediction:</b>  

The entered values are passed to the machine learning model to predict the best crop.

</p>



<p>

<b>4. Result Screen:</b>  

The predicted crop is displayed clearly to the user.

</p>



<p>

<b>5. History \& Profile:</b>  

All predictions are saved in Firestore and shown in the history section.

The profile screen displays user details and total predictions.

</p>



<hr/>



<h2 align="center">🚀 App Preview</h2>



<table align="center" cellpadding="10">

&nbsp;   <tr>

&nbsp;       <td align="center">

&nbsp;           <img src="images/splash.png" width="250"><br/>

&nbsp;           <small>Splash Screen</small>

&nbsp;       </td>

&nbsp;       <td align="center">

&nbsp;           <img src="images/login.png" width="250"><br/>

&nbsp;           <small>Login Screen</small>

&nbsp;       </td>

&nbsp;       <td align="center">

&nbsp;           <img src="images/signin.png" width="250"><br/>

&nbsp;           <small>Sign Up Screen</small>

&nbsp;       </td>

&nbsp;   </tr>



&nbsp;   <tr>

&nbsp;       <td align="center">

&nbsp;           <img src="images/home.png" width="250"><br/>

&nbsp;           <small>Home Screen</small>

&nbsp;       </td>

&nbsp;       <td align="center">

&nbsp;           <img src="images/result.png" width="250"><br/>

&nbsp;           <small>Result Screen</small>

&nbsp;       </td>

&nbsp;       <td align="center">

&nbsp;           <img src="images/history.png" width="250"><br/>

&nbsp;           <small>History Screen</small>

&nbsp;       </td>

&nbsp;   </tr>



&nbsp;   <tr>

&nbsp;       <td align="center">

&nbsp;           <img src="images/profile.png" width="250"><br/>

&nbsp;           <small>Profile Screen</small>

&nbsp;       </td>

&nbsp;   </tr>

</table>



<hr/>



<h2 align="center">⚡ Installation \& GitHub Upload Guide</h2>



<h3>Upload Project to GitHub</h3>

<pre>

git init

git add .

git commit -m "Initial commit - CropFit App"

git remote add origin https://github.com/svidhi08/CropFit.git

git push -u origin main

</pre>



<h3>Run Project Locally</h3>

<ol>

&nbsp;   <li>Clone the repository from GitHub</li>

&nbsp;   <li>Add <code>google-services.json</code> inside the <code>app/</code> folder</li>

&nbsp;   <li>Enable Email/Password Authentication and Firestore in Firebase Console</li>

&nbsp;   <li>Open the project in Android Studio and click Run</li>

</ol>



<hr/>



<h2 align="center">⚡ Future Enhancements</h2>

<br/>



<table border="1" align="center" cellpadding="8" cellspacing="0">

&nbsp;   <tr>

&nbsp;       <th>Feature</th>

&nbsp;       <th>Description</th>

&nbsp;       <th>Status</th>

&nbsp;   </tr>

&nbsp;   <tr>

&nbsp;       <td>Live Weather API</td>

&nbsp;       <td>Automatically fetch weather data using location</td>

&nbsp;       <td>Planned</td>

&nbsp;   </tr>

&nbsp;   <tr>

&nbsp;       <td>Crop Disease Detection</td>

&nbsp;       <td>Detect plant diseases using camera and AI</td>

&nbsp;       <td>Planned</td>

&nbsp;   </tr>

&nbsp;   <tr>

&nbsp;       <td>Multi-language Support</td>

&nbsp;       <td>Support for regional languages</td>

&nbsp;       <td>In Progress</td>

&nbsp;   </tr>

&nbsp;   <tr>

&nbsp;       <td>Delete History</td>

&nbsp;       <td>Allow users to remove old predictions</td>

&nbsp;       <td>Completed</td>

&nbsp;   </tr>

</table>



<br/>

<p align="center">

&nbsp;   Developed by <a href="https://github.com/svidhi08">svidhi08</a>

</p>



</body>

</html>



