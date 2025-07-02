<template>
    <div>
        <String label="Title" v-model="newValue.title" :editMode="editMode" />
        <String label="Content" v-model="newValue.content" :editMode="editMode" />

        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>

<script>
import BaseEntity from './base-ui/BaseEntity.vue';
import axios from 'axios';

export default {
    name: 'Book',
    mixins: [BaseEntity],
    props: {
        value: {
            type: Object,
            default: () => ({})
        },
        editMode: Boolean,
    },
    data: () => ({
        path: "books",
        // 2. 폼 데이터를 담을 별도의 'newValue' 객체를 만듭니다.
        newValue: {},
    }),
    created() {
        // 3. 컴포넌트가 생성될 때, 부모로부터 받은 value를 newValue에 복사합니다.
        //    이렇게 하여 부모의 데이터를 직접 수정하지 않는 안전한 구조가 됩니다.
        this.newValue = { 
            ...this.value,
            // 새 책 등록 시 필요한 기본값을 여기에 설정할 수 있습니다.
            userId: this.value.userId || 1, 
        };
    },
    methods: {
        async save() {
            try {
                // 4. 이제 this.value 대신, 사용자의 입력값이 담긴 this.newValue를 전송합니다.
                const response = await axios.post('/books', this.newValue);
                
                this.$emit('add', response.data);
                alert('성공적으로 저장되었습니다.');

            } catch (error) {
                console.error('책 저장에 실패했습니다:', error);
                alert('저장에 실패했습니다.');
            }
        }
    },
}
</script>