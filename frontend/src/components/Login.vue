<script setup>
import {ref} from 'vue'

const username = ref('')
const password = ref('')

const emit = defineEmits(['login', 'register'])

function login(){
    if(username.value.trim() === '' || password.value.trim() === ''){
        alert('Username and password are required!')
        return
    }

    emit('login', {
        username: username.value,
        password: password.value
    })
}
</script>

<template>
    <form class="login-form" @submit.prevent="login">
        <h2>Login</h2>

        <label>
            Username:
            <input
                type="text"
                v-model="username"
                placeholder="Enter username"
                required
            >
        </label>

        <label>
            Password:
            <input
                type="password"
                v-model="password"
                placeholder="Enter password"
                required
            >
        </label>

        <button type="submit">Login</button>

        <p>
            Don't have an account?
            <button type="button" @click="emit('register')">
                Register
            </button>
        </p>
    </form>
</template>

<style scoped>
.login-form{
    display:flex;
    flex-direction:column;
    gap:15px;
    background:white;
    padding:25px;
    width:fit-content;
    min-width:300px;
    margin:auto;
    border-radius:10px;
    color:black;
    box-shadow: 0px 0px 10px rgba(0,0,0,0.2);
}

.login-form h2{
    text-align:center;
}

.login-form label{
    display:flex;
    flex-direction:column;
    gap:5px;
}

.login-form input{
    padding:8px;
    border:1px solid #999;
    border-radius:5px;
    outline:none;
}

.login-form input:focus{
    border-color:#159bc3;
}

.login-form > button{
    padding:8px;
    border:none;
    border-radius:5px;
    background:#159bc3;
    color:white;
    font-weight:bold;
    cursor:pointer;
}

.login-form > button:hover{
    background:#1285a8;
}

.login-form p{
    text-align:center;
}

.login-form p button{
    border:none;
    background:none;
    color:#159bc3;
    font-weight:bold;
    cursor:pointer;
}
</style>