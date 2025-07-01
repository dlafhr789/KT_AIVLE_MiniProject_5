import { reactive } from 'vue'

const state = reactive<{ user: any | null }>({ user: null })

export function useAuth() {
  /* localStorage → state 복원 */
  function restore() {
    const saved = localStorage.getItem('user')
    const parsed = saved ? JSON.parse(saved) : null
    state.user = normalize(parsed)
  }

  /* 로그인/회원가입 성공 시 */
  function setUser(u: any) {
    state.user = normalize(u)
    localStorage.setItem('user', JSON.stringify(state.user))
  }

  function clear() {
    state.user = null
    localStorage.removeItem('user')
  }

  /* 🔑 핵심: role 이 null/undefined 면 'USER' 로 치환 */
  function normalize(u: any | null) {
    if (!u) return u
    return { ...u, role: u.role ?? 'USER' }
  }

  return { state, setUser, restore, clear }
}
