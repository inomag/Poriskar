const firebase = require('firebase');

const firebaseConfig = {
    apiKey: "AIzaSyAohHVnzFeWJ3mEPr0sR98j_Q4MEfCVlgM",
    authDomain: "fb-express-d6a56.firebaseapp.com",
    databaseURL: "https://fb-express-d6a56-default-rtdb.firebaseio.com",
    projectId: "fb-express-d6a56",
    storageBucket: "fb-express-d6a56.appspot.com",
    messagingSenderId: "496050874177",
    appId: "1:496050874177:web:362350fb284c273fe163c0",
    measurementId: "G-HP7RPHP9H6"
};

// Initialize Firebase
const db = firebase.initializeApp(firebaseConfig);

module.exports = db;