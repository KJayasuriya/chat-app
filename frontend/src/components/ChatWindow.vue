<script setup>
    import {ref,watch, computed} from 'vue'

    const props = defineProps({
        contact: Object,
        conversations: Object
    })
    const message = ref('')
    
    const currentMessages = computed(()=>{
        if(!props.contact) return []
        return props.conversations[props.contact.username] || []
    })
    watch(() => props.contact, (contact)=>{
        if(contact && !props.conversations[contact.username]){
            props.conversations[contact.username] = [];
            saveConversations();
        }
    })
    function saveConversations(){
        localStorage.setItem('conversations', JSON.stringify(props.conversations))
    }
    function getLastMessage(contact){
        const msgs = props.conversations[contact.username] || [];
        if(msgs.length === 0) return 'No messages yet';
        return msgs[msgs.length - 1].text;
    }
    function getTime(){
        return new Date().toLocaleTimeString([],
            {hour:'2-digit', minute:'2-digit'}
        )
    }
    function sendMessage(){
        const text = message.value.trim();
        if(text === '' || !props.contact){
            return;
        }
        const username = props.contact.username;
        
        props.conversations[username].push({
            sender: 'You',
            text:message.value,
            time:getTime()
        })
        saveConversations()
        message.value = '';
        setTimeout(()=>{
            const replies = [
                'Hey! Got your message dude 😎',
                'Haha yeah 😂',
                'What are you doing bro?',
                'Okay bro 👍',
                'Damn 😂',
                'Tell me more!',
                'Really? 👀',
                'Lol 😂',
                'I was waiting for your message!',
                'Yeah bro, I understand.'
            ]
            const randomReply = replies[Math.floor(Math.random() * replies.length)]
            props.conversations[username].push({
                sender:props.contact.username,
                text:randomReply,
                time: getTime()
            })
            saveConversations()
        },1000)
    }
</script>

<template>
    <section class="chat-window" v-if = "contact">
        <header>
            <h2>{{ contact.username }}</h2>
            <strong>{{ contact.phone }}</strong>
        </header>
        <div class="messages">
            <div class="message"  v-for="(msg,index) in currentMessages" :key = "index">
                <div class="sent" v-if = "msg.sender === 'You'">
                    <h3>{{ msg.sender }}</h3>
                    <p>{{ msg.text }}</p>
                    <small>{{ msg.time }}</small>
                </div>
                <div class="received" v-else>
                    <h3>{{ msg.sender }}</h3>
                    <p>{{ msg.text }}</p>
                    <small>{{ msg.time }}</small>
                </div>
            </div>
        </div>
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
        min-height:90vh;
        background:url('./../assets/background-dark.png');
        border-radius:10px;
        overflow:hidden;
        color:black;
    }
    .messages{
        flex:1;
        margin-left:5%;
        padding:15px;
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
    header{
        display:flex;
        justify-content:space-between;
        align-items:center;
        padding:15px;
        background:#159bc3;
        color:white;
    }

    .message-form{
        margin: auto;
        margin-bottom: 40px;
        background:white;
        border-radius: 30px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        gap: 5px;
        padding: 5px;

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
</style>