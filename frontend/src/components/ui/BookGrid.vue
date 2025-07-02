<template>
    <v-container>
        <v-snackbar
            v-model="snackbar.status"
            :timeout="snackbar.timeout"
            :color="snackbar.color"
        >
            
            <v-btn style="margin-left: 80px;" text @click="snackbar.status = false">
                Close
            </v-btn>
        </v-snackbar>
        <div class="panel">
            <div class="gs-bundle-of-buttons" style="max-height:10vh;">
                <v-btn @click="addNewRow" @class="contrast-primary-text" small color="primary">
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
            <div class="mb-5 text-lg font-bold"></div>
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
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="Title">{{ val.title }}</td>
                            <td class="whitespace-nowrap" label="Content">{{ val.content }}</td>
                            <td class="whitespace-nowrap" label="UserId">{{ val.userId }}</td>
                            <td class="whitespace-nowrap" label="PublishedAt">{{ val.publishedAt }}</td>
                            <td class="whitespace-nowrap" label="State">{{ val.state }}</td>
                            <td class="whitespace-nowrap" label="View">{{ val.view }}</td>
                            <td class="whitespace-nowrap" label="Subscribers">{{ val.subscribers }}</td>
                            <v-row class="ma-0 pa-4 align-center">
                                <v-spacer></v-spacer>
                                <Icon style="cursor: pointer;" icon="mi:delete" @click="deleteRow(val)" />
                            </v-row>
                        </tr>
                    </tbody>
                </v-table>
            </div>
        </div>
        <v-col>
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
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <Book :offline="offline"
                            :isNew="!value.idx"
                            :editMode="true"
                            :inList="false"
                            v-model="newValue"
                            @add="append"
                        />
                    </v-card-text>
                </v-card>
            </v-dialog>
            <v-dialog
                v-model="editDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">Book 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <String label="Title" v-model="selectedRow.title" :editMode="true"/>
                            <String label="Content" v-model="selectedRow.content" :editMode="true"/>
                            <Number label="UserId" v-model="selectedRow.userId" :editMode="true"/>
                            <Date label="PublishedAt" v-model="selectedRow.publishedAt" :editMode="true"/>
                            <String label="State" v-model="selectedRow.state" :editMode="true"/>
                            <Number label="View" v-model="selectedRow.view" :editMode="true"/>
                            <Number label="Subscribers" v-model="selectedRow.subscribers" :editMode="true"/>
                            <v-divider class="border-opacity-100 my-divider"></v-divider>
                            <v-layout row justify-end>
                                <v-btn
                                    width="64px"
                                    color="primary"
                                    @click="save"
                                >
                                    수정
                                </v-btn>
                            </v-layout>
                        </div>
                    </v-card-text>
                </v-card>
            </v-dialog>
        </v-col>
    </v-container>
</template>

<script>
import { ref, onMounted } from 'vue'; // ref, onMounted를 import 합니다.
import axios from 'axios';
import BaseGrid from '../base-ui/BaseGrid.vue';
import Book from '../Book.vue';
import OpenBook from '../OpenBook.vue';

export default {
  name: 'bookGrid',
  mixins: [BaseGrid],
  components: {
    Book,
    OpenBook
  },
  // setup() 함수를 사용하여 Vue 3 Composition API 스타일로 데이터를 관리합니다.
  setup() {
    // 1. 테이블에 표시될 데이터를 담을 변수를 빈 배열로 초기화합니다.
    const items = ref([]); 
    const openDialog = ref(false);
    const editDialog = ref(false);
    const selectedRow = ref(null);
    const newValue = ref({});

    // 2. 컴포넌트가 로드될 때, 백엔드에서 데이터를 가져와 items에 저장합니다.
    onMounted(async () => {
      try {
        const response = await axios.get('/books');
        if (response.data && response.data._embedded && response.data._embedded.books) {
          const data = response.data._embedded.books;
          data.forEach(obj => {
            if (obj._links && obj._links.self && obj._links.self.href) {
              obj.id = obj._links.self.href.split("/").pop();
            }
          });
          items.value = data;
        }
      } catch (e) {
        console.error("데이터 로딩 실패:", e);
      }
    });

    // 3. '등록' 버튼을 누르면 팝업창을 엽니다.
    const addNewRow = () => {
      newValue.value = { userId: 1 }; // 새 책을 위한 기본값
      openDialog.value = true;
    };

    // 4. 자식(Book.vue)으로부터 'add' 이벤트를 받았을 때 실행될 append 함수
    const append = (newBook) => {
      // 이제 items는 항상 배열이므로 .unshift가 안전하게 동작합니다.
      items.value.unshift(newBook);
      openDialog.value = false;
    };
    
    // ... 다른 메소드들(openEditDialog, closeDialog, openBook 등)도
    // ref 변수를 사용하도록 여기에 함께 정의해야 합니다.

    return {
      items,
      headers: ref([ /* 헤더 정의 */ ]),
      openDialog,
      editDialog,
      selectedRow,
      newValue,
      addNewRow,
      append,
    };
  }
}
</script>