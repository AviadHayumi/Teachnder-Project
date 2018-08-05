const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const app = express();

var IsProduction = 1;




//Connect to mongoose
if (IsProduction == 0) {
    mongoose.connect('mongodb://localhost/teachnder-try', {
    })
        .then(() => console.log("MongoDB Connected..."))
        .catch(err => console.log(err));
}
else {
    mongoose.connect('mongodb://aviad_haim1:aviad_haim1@ds259351.mlab.com:59351/myfbdb', {
    })
        .then(() => console.log("MongoDB Connected..."))
        .catch(err => console.log(err));
}


//Load Idea Model
require('./models/Idea');
const Idea = mongoose.model('ideas');


require('./models/User');
const User = mongoose.model('users');


require('./models/Chat');
const Chat = mongoose.model('chats');

//Bodyparser MiddleWare

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());


//Login
app.get('/Login/:email/:password/', (req, res) => {
    var email = req.params.email;
    var password = req.params.password;

    User.findOne({ password: password, email: email })
        .then(user => {
            if (user) {
                
                var output =
                {
                    message: 'successfully',
                    content: 'Logged In',
                    userContent: user
                };
                res.send(output);
                
            } else {

                var output =
                {
                    message: 'error',
                    content: 'email or password not exists'
                };
                res.send(output);
                
            }
        });

});

// Set more information
app.get('/moreInfo/:email/:name/:phone/:about', (req, res) => {

    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                user.phone = req.params.phone;
                user.name = req.params.name;
                user.about = req.params.about;
                user.save();
                var output =
                {
                    message: 'sucessfully',
                    content: 'user information was added sucessfully',
                    userContent: user
                };

                res.send(output);
            }
        });

});

// Register
app.get('/Register/:email/:password/:name/:type', (req, res) => {


    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                const newUser = {
                    email: req.params.email,
                    password: req.params.password,
                    name:req.params.name,
                    type:req.params.type
                }
                new User(newUser)
                    .save()
                    .then(user => {

                        var output =
                        {
                            message: 'successfully',
                            content: 'add succesfuly'
                        };
                        res.send(output);
                    });

            } else {
                var output =
                {
                    message: 'error',
                    content: 'Email has alredy taken'
                };
                res.send(output);

            }
        });

});

// Register
app.get('/Register/:email/:password/', (req, res) => {


    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                const newUser = {
                    email: req.params.email,
                    password: req.params.password,
                }
                new User(newUser)
                    .save()
                    .then(user => {

                        var output =
                        {
                            message: 'successfully',
                            content: 'add succesfuly'
                        };
                        res.send(output);
                    });

            } else {
                var output =
                {
                    message: 'error',
                    content: 'Email has alredy taken'
                };
                res.send(output);

            }
        });

});

//find 20 client
app.get('/find20businessOwner', (req, res) => {

    User.find({ type: 'business owner' }).limit(20).then(user => {
        if (!user) {
            var output =
            {
                message: 'error',
                content: 'there are not business owners'
            };
            res.send(output);
        } else {
            var output =
            {
                message: 'sucessfully',
                usersContent: user
            };
            res.send(output);
        }
    });

});

//find with about
app.get('/findWithAbout/:about', (req, res) => {

    User.find({ 'about': new RegExp(req.params.about, 'i') }).then(user => {
        if (!user) {
            var output =
            {
                message: 'error',
                content: 'user is not exist'
            };
            res.send(output);
        } else {
            var output =
            {
                message: 'sucessfully',
                usersContent: user
            };
            res.send(output);

        }
    });

});


//find 20 client
app.get('/isUserNotSeen/:myemail/:otheremail', (req, res) => {

    User.findOne({ name: req.params.otherEmail }).then(user => {

        if (!user) {
            var output =
            {
                message: 'error',
                content: 'user not exist'
            };
            res.send(output);
        } else {

        
            user.yeps.findOne({useremail:rec.params.myemail}).then(yep=> {
                if(!yep) {
                    user.nope.find({useremail: myemail}).then(nope => {
                        if(nope) {
                            res.send('already seen')
                        } else {
                            res.send('not seen');
                        }
                    })
                    
                }else {
                    res.send('alredy seen');
                }
            }).catch(function() {
                res.send('user not exist');
            });
            
        }

    });

});



//find 20 client
app.get('/find20client/', (req, res) => {

    User.find({ type: 'client' }).limit(20).then(user => {

        if (!user) {
            var output =
            {
                message: 'error',
                content: 'there are not clients'
            };
            res.send(output);
        } else {
            var output =
            {
                message: 'sucessfully',
                usersContent: user
            };
            res.send(output);
        }

    });

});

//find 20 users
app.get('/find20/', (req, res) => {

    User.find().limit(20).then(user => {
        if (!user) {
            var output =
            {
                message: 'error',
                content: 'there are no users'
            };
            res.send(output);
        } else {
            var output =
            {
                message: 'sucessfully',
                usersContent: user
            };
            res.send(output);
        }
    });

});


//Check Match

app.get('/CheckMatch/:myemail/:otherEmail', (req, res) => {

    email1 = req.params.myemail;
    email2 = req.params.otherEmail;

});


//Set nope match
app.get('/SetNope/:myemail/:otherEmail', (req, res) => {

    const nope = {
        useremail: req.params.myemail
    };
    User.findOne({ email: req.params.otherEmail })
        .then(user => {
            if (!user) {
                res.send('user is not exist');
            } else {
                user.nopes.push(nope);
                user.save();

                var output =
                {
                    message: 'sucessfully',
                    content: req.params.myemail + 'was added sucessfully to nopes',
                    userContent: user
                };
                res.send(output);
            }
        });
});


//Set Match
app.get('/SetMatch/:myemail/:otherEmail/:matchid', (req, res) => {

    const match = {
        useremail: req.params.myemail,
        matchid: req.params.matchid
    };

    User.findOne({ email: req.params.otherEmail })
        .then(user => {
            if (!user) {
                res.send('user is not exist');
            } else {
                user.matches.push(match);
                user.save();

                var output =
                {
                    message: 'sucessfully',
                    content: req.params.myemail + 'was added sucessfully to match',
                    userContent: user
                };
                res.send(output);
            }
        });
});


//Set yep match
app.get('/SetYep/:myemail/:otherEmail', (req, res) => {

    const yep = {
        useremail: req.params.myemail
    };

    User.findOne({ email: req.params.otherEmail })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                user.yeps.push(yep);
                user.save();

                var output =
                {
                    message: 'sucessfully',
                    content: req.params.myemail + 'was added sucessfully to yeps',
                    userContent: user
                };
                res.send(output);

            }
        });
});


//get all users
app.get('/GetAllUsers', (req, res) => {

    User.find()
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'no users'
                };
                res.send(output);
            } else {
                var output =
                {
                    message: 'sucessfully',
                    usersContent: user
                };
                res.send(output);
                user.save();
            }
        });

});


//Get Matches of user
app.get('/GetUserMatches/:email', (req, res) => {

    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                var output =
                {
                    message: 'sucessfully',
                    userEmail: user.matches,
                    userName: user.name
                };
                res.send(output);
                user.save();
            }
        });
});


//Get yeps of user
app.get('/GetUserYeps/:email', (req, res) => {

    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                var output =
                {
                    message: 'sucessfully',
                    userContent: user.yeps
                };
                res.send(output);
                user.save();
            }
        });
});

//Get nopes of users
app.get('/GetUserNopes/:email', (req, res) => {

    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                var output =
                {
                    message: 'sucessfully',
                    userContent: user.nopes
                };
                res.send(output);
                user.save();
            }
        });
});

//get user by email
app.get('/GetUserByEmail/:email', (req, res) => {

    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                var output =
                {
                    message: 'sucessfully',
                    userContent: user
                };
                res.send(output);
                user.save();

            }
        });

});

//set user type
app.get('/SetType/:email/:type', (req, res) => {

    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                user.type = req.params.type;
                user.save();
                var output =
                {
                    message: 'sucessfully',
                    content: 'user type was added sucessfully',
                    userContent: user
                };
                res.send(output);
            }
        });
});


//set user img
app.get('/SetImg/:email/:img', (req, res) => {

    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                user.imgUrl = req.params.img;
                user.save();
                var output =
                {
                    message: 'sucessfully',
                    content: 'user image url was added sucessfully',
                    userContent: user
                };
                res.send(output);
            }
        });
});

//set user name
app.get('/SetName/:email/:name', (req, res) => {

    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                user.name = req.params.name;
                user.save();
                var output =
                {
                    message: 'sucessfully',
                    content: 'user name was added sucessfully',
                    userContent: user
                };
                res.send(output);
            }
        });
});

//set user phone
app.get('/SetPhone/:email/:phone', (req, res) => {

    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                user.phone = req.params.phone;
                user.save();
                var output =
                {
                    message: 'sucessfully',
                    content: 'user phone was added sucessfully',
                    userContent: user
                };
                res.send(output);
            }
        });
});

//set user about
app.get('/SetAbout/:email/:about', (req, res) => {

    User.findOne({ email: req.params.email })
        .then(user => {
            if (!user) {
                var output =
                {
                    message: 'error',
                    content: 'user is not exist'
                };
                res.send(output);
            } else {
                user.about = req.params.about;
                user.save();
                var output =
                {
                    message: 'sucessfully',
                    content: 'user about was added sucessfully',
                    userContent: user
                };

                res.send(output);
            }
        });
});

//Set yep match
app.get('/AddMessageToChat/:_id/:email/:content', (req, res) => {

    const message = {
        useremail: req.params.email,
        content: req.params.content
    };

    Chat.findOne({ _id: req.params._id })
        .then(chat => {
            if (!chat) {
                var output =
                {
                    message: 'error',
                    content: 'chat is not exist'
                };
                res.send(output);
            } else {
                chat.messages.push(message);
                chat.save();

                var output =
                {
                    message: 'sucessfully',
                    content: 'message added'
                };
                res.send(output);
            }
        });
});


//Create Chat
app.get('/CreateChat/:user1/:user2', (req, res) => {


    const chat = {
        user1: req.params.user1 ,
        user2: req.params.user2
    }

    new Chat(chat).save()
    .then( (chat)=> {
        if(!chat) {
            var output =
            {
                message: 'error',
            };
            res.send(output);

        }else{
            var output =
            {
                message: 'successfully',
                content: chat
            };
            res.send(output);
        }
       
    })

});


//Set yep match
app.get('/ShowChatMessages/:_id', (req, res) => {

    

    Chat.findOne({ _id: req.params._id })
        .then(chat => {
            if (!chat) {
                var output =
                {
                    message: 'error',
                    content: 'chat is not exist'
                };
                res.send(output);
            } else {
                var output =
                {
                    message: 'sucessfully',
                    chatContent: chat
                };
                res.send(output);
            }
        });
});

//connect to port
// Debug
var port = 7000;

// Production
if (IsProduction == 1) {
    port = process.env.PORT || 7000;
}

app.listen(port, () => {
    console.log('Server started on port ' + port);
});

