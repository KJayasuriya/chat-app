<script setup>
import { ref } from 'vue'

const name = ref('')
const phone = ref('')
const pwd = ref('')

function login(){
    if(name.value.trim() === ''){
        alert('Username cannot be empty!')
        return
    }

    if(phone.value.length !== 10){
        alert('Phone must be 10 digits long!')
        return
    }

    if(pwd.value.trim() === ''){
        alert('Password cannot be empty!')
        return
    }

    console.log({
        name: name.value,
        phone: phone.value,
        password: pwd.value
    })
}
</script>

<template>
    <form @submit.prevent="login">
        <h2>Login</h2>

        <label>User Name</label>
        <input v-model="name" type="text" required>

        <label>Phone Number</label>
        <input v-model="phone" type="tel" required>

        <label>Password</label>
        <input v-model="pwd" type="password" required>

        <button type="submit">Login</button>
    </form>
</template>

<style scoped>
form{
    display:flex;
    flex-direction:column;
    margin:auto;
    width:400px;
    background:white;
    padding:15px;
    border-radius:10px;
    gap:10px;
}

input{
    border-radius:2px;
    border:.5px solid black;
    outline:none;
    padding:5px;
}

input:focus{
    outline:1px solid #00a944;
}

button{
    background-color:#159bc3;
    color:white;
    font-weight:bold;
    width:fit-content;
    padding:8px 15px;
    border:none;
    border-radius:10px;
    margin:auto;
    cursor:pointer;
    transition:.3s;
}

button:active{
    transform:translateY(5px);
}
</style>