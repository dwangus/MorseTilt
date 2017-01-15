var express = require('express');
var app = express();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var bodyParser = require('body-parser');

var admin = require("firebase-admin");
//var serviceAccount = require("/credentials.json");

admin.initializeApp({
  credential: admin.credential.cert({
  "type": "service_account",
  "project_id": "tilt-58af2",
  "private_key_id": "9ec43b9352221fd63a9ded5b5ed5bffb53e50939",
  "private_key": "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCz7if1n1c8d1w4\nb1OxepdA0+rMMb2+oiAe5MVXxVDx5P80Zc5ua52jjRmVwDrY+xwWeH0+9tvDllhW\nd/ks/RWYF0iLLdk3eDnNSrVzl/A9aZw4uwQ+TxdOmUP9gTyoO9s3CD1DsbvskvVM\nSpcb0MwgkQ14rDjtGiWYGLciU7Xyps5eNanIyfFCdGnlF6I4Hw59wkB9TLSQ3RYa\nfNFgPx0umaalHJVVn4V5usrqYzheFMpsZQec8TrmHD4xHAdIHi1TSoMknOQH9MOV\nEZndYM+/8KiQrWRYEbDAE8UjhM5ZLQ+mMpbS4Jp5lcEMyzkdEaSKCLIrSK6MIiSE\n2QSQEM3PAgMBAAECggEATGjLsWQcUMJDeHfWa90qlE4qPNK2yFjjGRt/rplNmZvs\nwJynQH8U75qBDfYXR4PD1ssOLQn6I6IaG4ZRW8iSPgdNOEWdJBA7Q7IBQnh4fiv2\nOqU41RkTRb6Sf/o8/xiELGekVLYKnxl9cAuRBi/yL7ZJqUBB4HalFmUQiFRSRRvY\nnRsuQe8GPeq4XQRGPOVgJJoY+EhLGO6hJ1Ckzg/geCbx0s0Mb8C4JmAdw5K0Jjir\nZxqH2+Bg+aNhd6cbWDmI7hVWAGheb+t7HoEpDTlTAHu11hVm4WEmEs8hbGpnmCFq\nRnJs0eZfgg0vK1MnU6s91xD8gq2HdDT2OKHM7JlbMQKBgQDjKyBUOtunsLclmG+N\nE2W4aKHr7zEvnxfMWG7p57eqCpcG8DPiZ3zBrO0wa7+FwybBs2Ow1jync23mTtI5\nVZdIW4p5qxJ0RTCZw75S+D+Yjz/XAX1q1dsUgi4YyIMq8xz9sSY5AeuHXq+uDgZk\nl8h+hojqK0GE7yrp0kZElzh4uQKBgQDKxDn1cuAz+zL1GpV9hmoOGv4dxB4xStTz\nwPPOql5rjT68R3fHCils5JQYDi0BH4tijAHkJse80BsMebAzaV79jKHyxol2hNpN\nsQSd00pnYdvRbFnYkj7uQUCwZdNE6HV4JKvKTt9C83yMZAMuEYc0i1VJ5LoR2y4C\nyMvbhHumxwKBgD5taZL8IcTxgUMpou4n+Ho29th5gRDPLaEQZFqPPR/TjTFw7TE4\ns0YeGYiG87j0qj/s5maAWWbDB96Uppzu8oYF8gYBhy7AnNyEuFbgtcldBHQ3ipnD\nb1Mxey2AXNt+t+bXueNMrccjtC+m1wbp10U9/34qqPS5LRfYm0raK8CxAoGAYfp3\n1zbvR672bKGv4rv0u2QnrtJ5lHAIftCZ/x8sCORzkB8orbEnW0sIlKck8AoCHFhz\nAYALvB0DNQAIOI+OKw6totZ0ziRcKHaFOI5EwbfztDqbH59rcU1uqM5LTFzXhnqR\n2GxMTdnCMUSSlMw7fRRm3hH8J2y1yvqkJyrZZTkCgYEApQB+nwBW0ATaf0aWfCtB\n6s0udUAYyyWvtlD1oZn2jv4ZP3tv5QbdWf+UZ5uxh3jUGuHHynz3gWZ0ATgAejGU\nZl/MET84XUARFse4zZSp1wiPQOlscId/IzcgMIHnGxslzsqo871bLGT5NxJPZ6PV\nHljzPcyrczmLm+GvAkMOZsI=\n-----END PRIVATE KEY-----\n",
  "client_email": "firebase-adminsdk-rnd7y@tilt-58af2.iam.gserviceaccount.com",
  "client_id": "106442685487063698976",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://accounts.google.com/o/oauth2/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-rnd7y%40tilt-58af2.iam.gserviceaccount.com"
}),
  databaseURL: "https://tilt-58af2.firebaseio.com/"
});

var db = admin.database();
var ref = db.ref("phones");

app.use(express.static(__dirname + '/public'));
app.get('/', function(req, res) {
    res.sendFile(__dirname + '/index.html');
});

io.on("connection", function(socket) {
    console.log("User " + socket.id + " has joined.");
    socket.on('disconnect', function(){
      console.log("User " + socket.id + " has disconnected");
    });
});

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.post('/updatedata', function(req, res) {
    var messageRef = ref.child('812-562-6455/messages/' + req.body.dateandtime);
    if (req.body.orientation === "Portrait") {
      req.body.orientation = 1;
    } else {
      req.body.orientation = 0;
    }
    /*messageRef.set(req.body.orientation, function(err) {
      if (err) {
        console.error(err);
      } else {
        //io.emit('log', req.body);
        io.emit('updatedata');
      }
    });*/

    io.emit('updatedata');
});

http.listen((process.env.PORT || 3000), function(){
  console.log('listening on *:3000');
});