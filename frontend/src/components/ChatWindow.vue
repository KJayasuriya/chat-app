<script setup>
import {ref, watch, computed, nextTick} from 'vue'

const props = defineProps({
    contact: Object,
    currentUser: String,
    conversations: Object
})

const emit = defineEmits(['back'])

const message = ref('')
const messagesContainer = ref(null)

const currentMessages = computed(() => {
    if(!props.contact) return []

    const messages = props.conversations[props.contact.username] || []

    return messages.map(msg => ({
        ...msg,
        sender: msg.sender === props.currentUser ? 'You' : msg.sender
    }))
})

function scrollToBottom(){
    if(messagesContainer.value){
        messagesContainer.value.scrollTo({
            top: messagesContainer.value.scrollHeight,
            behavior: 'smooth'
        })
    }
}

function getTime(){
    return new Date().toLocaleTimeString([], {
        hour: '2-digit',
        minute: '2-digit'
    })
}

async function loadMessages(){

    if(!props.contact || !props.currentUser){
        return
    }

    const username = props.contact.username

    try{

        const response = await fetch(
            `http://localhost:8080/api/chat/${encodeURIComponent(props.currentUser)}/${encodeURIComponent(username)}`
        )

        if(!response.ok){
            alert('Unable to load messages')
            return
        }

        const messages = await response.json()

        props.conversations[username] = messages

        await nextTick()
        scrollToBottom()

    }catch(error){

        console.error(error)
        alert('Unable to connect to server')

    }
}

watch(
    () => props.contact,
    (contact) => {
        if(contact){
            loadMessages()
        }
    },
    {immediate: true}
)

async function sendMessage(){

    const text = message.value.trim()

    if(text === '' || !props.contact){
        return
    }

    const username = props.contact.username

    const data = {
        sender: props.currentUser,
        receiver: username,
        message: text,
        time: getTime()
    }

    try{

        const response = await fetch('http://localhost:8080/api/chat',{
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })

        if(!response.ok){
            const error = await response.text()
            alert(error)
            return
        }

        const savedMessage = await response.json()

        if(!props.conversations[username]){
            props.conversations[username] = []
        }

        props.conversations[username].push(savedMessage)

        message.value = ''

        await nextTick()
        scrollToBottom()

    }catch(error){

        console.error(error)
        alert('Unable to connect to server')

    }
}
</script>

<template>
    <section class="chat-window" v-if = "contact">
        <div class="chat-header">
            <button class="back-btn" @click="emit('back')">←</button>
            <h2>{{ contact.username }}</h2>
        </div>
        <div class="messages" ref = "messagesContainer">
            <div class="message"  v-for="(msg,index) in currentMessages" :key = "index">
                <div class="sent" v-if = "msg.sender === 'You'">
                    <h3>{{ msg.sender }}</h3>
                    <p>{{ msg.message }}</p>
                    <small>{{ msg.time }}</small>
                </div>
                <div class="received" v-else>
                    <h3>{{ msg.sender }}</h3>
                    <p>{{ msg.message }}</p>
                    <small>{{ msg.time }}</small>
                </div>
            </div>
        </div>
        <button class="scroll-bottom-btn" @click="scrollToBottom">↓</button>
        <form @submit.prevent="sendMessage" class="message-form">
            <input v-model="message" type = "text" placeholder = "Type a message">
            <button type="submit">🠝</button>
        </form>
    </section>
    <section v-else class="no-chat">
        <h2>Select a contact to Start chatting</h2>
    </section>
</template>

<style scoped>
    *{
        --sent-box-color: rgb(169, 253, 104);
    }
    .chat-window{
        display:flex;
        flex-direction:column;
        flex:1;
        min-height:0;
        background:url('./../assets/background-dark.png');
        border-radius:10px;
        overflow:hidden;
        color:black;
        position:relative;
    }
    .chat-header{
        display:flex;
        align-items:center;
        background:#159bc3;
        gap:10px;
    }
    .back-btn{
        display: none;
        border:none;
        font-weight:bold;
        color:white;
        background:none;
        font-size: 1.8rem;
        cursor:pointer;
    }
    .messages{
        flex:1;
        min-height:0;
        margin-left:5%;
        padding:15px;
        padding-bottom:120px;
        display:flex;
        flex-direction:column;
        gap:20px;
        overflow-y:auto;
    }
    .message{
        display:flex;
        width: 100%;
    }
    .sent, .received{
        padding: 10px;
        width: clamp(8rem,300px,15rem);
        overflow-wrap: break-word;
        word-break: break-word;
        white-space: pre-wrap;
    }
    .sent small,.received small{
        display:block;
        text-align:right;
        font-size:.7rem;
        opacity:.6;
        margin-top:5px;
    }
    .sent{
        position:relative;
        margin-left: auto;
        border-radius: 10px;
        border-top-right-radius: 0;
        background-color: var(--sent-box-color);
    }
    .sent::after{
        position:absolute;
        content:"";
        background-color: var(--sent-box-color);
        width: 10px;
        height: 10px;
        top: 0px;
        right: -9px;
        clip-path: polygon(0% 0%, 100% 0%, 0% 100%);
    }
    .received{
        position:relative;
        margin-right: auto;
        border-radius: 10px;
        border-top-left-radius: 0;
        background-color: white;
    }
    .received::before{
        position:absolute;
        content:"";
        background-color: white;
        width: 10px;
        height: 10px;
        top: 0px;
        left: -9px;
        clip-path: polygon(0% 0%, 100% 0%, 100% 100%);
    }
    .chat-header{
        display:flex;
        justify-content:space-between;
        align-items:center;
        padding:15px;
        background:#159bc3;
        color:white;
    }
    .scroll-bottom-btn{
        position:absolute;
        right:45%;
        bottom:75px;
        z-index:99;
        border:none;
        border-radius:50%;
        width:40px;
        height:40px;
        background: #000000a2;;
        border: 0.5px solid white;
        color:white;
        font-size:1.5rem;
        cursor:pointer;
    }
    .message-form{
        background:white;
        border-radius:30px;
        display:flex;
        justify-content:space-between;
        align-items:center;
        gap:5px;
        padding:5px;
        position:absolute;
        bottom:20px;
        left:50%;
        transform:translateX(-50%);
        z-index:99;
    }
    .message-form input{
        padding: 5px;
        border:none;
        outline:none;
    }
    .message-form button{
        background-color: #159bc3;
        color: white;
        font-weight: bold;
        border-radius: 50%;
        border: none;
        aspect-ratio: 1;
        text-align: center;
        padding:8px 15px;
        cursor: pointer;
        transition: 0.3s;
    }
    .message-form button:active{
        transform: scale(0.9);
    }
    @media (max-width:768px){
        .back-btn{
            display:block;
        }

        .message-form{
            width:80%;
            bottom:20px;
            position:fixed;
            left:50%;
            z-index:99;
        }

        .scroll-bottom-btn{
            right: 45%;
            bottom:80px;
            position:fixed;
            z-index:99;
        }
    }
</style>