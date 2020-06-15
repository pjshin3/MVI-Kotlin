const express = require('express');
const app = express();
let users= [
  {
    id: 12345678,
    name: 'philip'
  }
]
let serverinfo= [
  {
    version: 1.0
  }
]

app.get('/users',(req,res)=>{
  console.log('who get in here post/users');
  res.json(users)
});

app.get('/servserinfo',(req,res)=>{
  console.log('servserinfo sand');
  res.json(servserinfo)
});

app.listen(3000, () =>{
  console.log('Example app listening on post 3000!');
});
