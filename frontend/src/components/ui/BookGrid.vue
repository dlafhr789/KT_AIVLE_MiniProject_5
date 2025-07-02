<template>
    <v-container>
        <v-snackbar
            v-model="snackbar.status"
            :timeout="snackbar.timeout"
            :color="snackbar.color"
        >
            {{ snackbar.text }}
            <v-btn style="margin-left: 80px;" text @click="snackbar.status = false">
                Close
            </v-btn>
        </v-snackbar>
        <div class="panel">
            <div class="gs-bundle-of-buttons" style="max-height:10vh;">
                <v-btn @click="addNewRow" class="contrast-primary-text" small color="primary">
                    <v-icon small style="margin-left: -5px;">mdi-plus</v-icon>등록
                </v-btn>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="openEditDialog()" class="contrast-primary-text" small color="primary">
                    <v-icon small>mdi-pencil</v-icon>수정
                </v-btn>
                <v-btn :disabled="!selectedRow || !hasRole('User')" style="margin-left: 5px;" @click="openBookDialog = true" class="contrast-primary-text" small color="primary">
                    <v-icon small>mdi-minus-circle-outline</v-icon>도서 열람
                </v-btn>
                <v-dialog v-model="openBookDialog" width="500">
                    <OpenBook
                        @closeDialog="openBookDialog = false"
                        @openBook="openBook"
                    ></OpenBook>
                </v-dialog>
            </div>

            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Title</th>
                            <th>Content</th>
                            <th>UserId</th>
                            <th>PublishedAt</th>
                            <th>State</th>
                            <th>View</th>
                            <th>Subscribers</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="selectedRow = val"
                            :key="val.bookId"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td>{{ val.title }}</td>
                            <td>{{ val.content }}</td>
                            <td>{{ val.userId }}</td>
                            <td>{{ val.publishedAt }}</td>
                            <td>{{ val.state }}</td>
                            <td>{{ val.view }}</td>
                            <td>{{ val.subscribers }}</td>
                            <td>
                                <Icon style="cursor: pointer;" icon="mi:delete" @click.stop="deleteRow(val)" />
                            </td>
                        </tr>
                    </tbody>
                </v-table>
            </div>
        </div>
        <v-dialog
            v-model="openDialog"
            transition="dialog-bottom-transition"
            width="35%"
        >
            <v-card>
                <v-toolbar
                    color="primary"
                    class="elevation-0 pa-4"
                    height="50px"
                >
                    <div style="color:white; font-size:17px; font-weight:700;">Book 등록</div>
                    <v-spacer></v-spacer>
                    <v-icon color="white" small @click="closeDialog()">mdi-close</v-icon>
                </v-toolbar>
                <v-card-text>
                    <Book 
                        :offline="offline"
                        :isNew="true"
                        :editMode="true"
                        :inList="false"
                        v-model="newValue"
                        @add="append"
                    />
                </v-card-text>
            </v-card>
        </v-dialog>
    </v-container>
</template>

<script>
import BaseGrid from '../base-ui/BaseGrid.vue'
import Book from '../Book.vue';
import OpenBook from '../OpenBook.vue'
import axios from 'axios';

export default {
    name: 'bookGrid',
    mixins: [BaseGrid],
    components: {
        Book,
        OpenBook,
    },
    data: () => ({
        path: 'books',
        openBookDialog: false,
        openDialog: false,
        newValue: {},
        value: [],
        selectedRow: null,
        snackbar: {
            status: false,
            timeout: 3000,
            color: 'success',
            text: ''
        },
    }),
    created() {
        this.search();
    },
    methods: {
        async search() {
            try {
                const response = await axios.get(`/${this.path}`);
                this.value = response.data;
            } catch (error) {
                console.error('데이터 로드 실패:', error);
            }
        },
        append(newBook) {
            if (Array.isArray(this.value)) {
                this.value.unshift(newBook);
            }
            this.openDialog = false;
        },
        addNewRow() {
            this.newValue = {};
            this.openDialog = true;
        },
        openEditDialog() {
            // 수정 로직 추가 필요
        },
        closeDialog() {
            this.openDialog = false;
        },
        async deleteRow(book) {
            try {
                await axios.delete(`/${this.path}/${book.bookId}`);
                this.value = this.value.filter(v => v.bookId !== book.bookId);
                this.selectedRow = null;
                this.snackbar = {
                    status: true,
                    timeout: 3000,
                    color: 'success',
                    text: '삭제되었습니다.'
                };
            } catch (e) {
                console.error('삭제 실패:', e);
            }
        },
        async openBook(params){
            try{
                const response = await axios.post('/openBook', { ...this.selectedRow, ...params });
                const updatedBook = response.data;
                const index = this.value.findIndex(b => b.bookId === this.selectedRow.bookId);
                if(index !== -1) this.value[index] = updatedBook;
                this.openBookDialog = false;
            } catch(e) {
                console.log(e);
            }
        },
        hasRole(role) {
            // 권한 체크 로직 구현 필요
            return true;
        },
    }
}
</script>
