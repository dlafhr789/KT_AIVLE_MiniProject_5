<template>
    <div>
        <String
            label="Title"
            v-model="value.title"
            :editMode="editMode"
        />
        <String
            label="Content"
            v-model="value.content"
            :editMode="editMode"
        />
        <!-- <Number
            label="UserId"
            v-model="value.userId"
            :editMode="editMode"
        /> -->
        <!-- <Date
            label="PublishedAt"
            v-model="value.publishedAt"
            :editMode="editMode"
        />
        <String
            label="State"
            v-model="value.state"
            :editMode="editMode"
        />
        <Number
            label="View"
            v-model="value.view"
            :editMode="editMode"
        />
        <Number
            label="Subscribers"
            v-model="value.subscribers"
            :editMode="editMode"
        /> -->
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'
import axios from 'axios';
import api from '@/plugins/axios'

export default {
    name: 'Book',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "books",
        value: {
            title: '',
            content: '',
            userId: 99, // 임시로 사용자 ID 1을 사용
        }
    }),
    created(){
        const user = JSON.parse(window.localStorage.getItem('user'))
        this.value.userId = user.id
    },
    computed:{
    },
    methods: {
        async save() {
            try {
                const headers = {
                    'Content-Type': 'application/json',
                    'X-User-Id': this.value.userId // 임시로 사용자 ID 1을 헤더에 추가
                };

                const response = await api.post('/books', this.value, { headers: headers });
                
                this.$emit('add', response.data);

                alert('성공');

            } catch (error) {
                console.error('도서 저장 실패', error);
                if (error.response) {
                    console.error('error.response : ', error.response)
                }
                alert('실패');
            }
        }
    },
}
</script>
