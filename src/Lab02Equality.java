import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import java.util.function.*;
import java.util.stream.*;
import java.io.*;
import java.lang.ref.*;

public class Lab02Equality {
	static final class User {
		private final int id;

		User(int id) {
			this.id = id;
		}

//     A complex or much complex hashcode implementation for this problem
//		@Override
//		public int hashCode() {
//			return Objects.hash(Integer.valueOf(id));
//		}
//		
        
		/**
		 *A simpler hashcode implementation for this problem
		 */
		@Override
		public int hashCode() {
			return Integer.hashCode(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			User other = (User) obj;
			return id == other.id;
		}

				
	}

	public static void main(String[] args) throws Exception {
		User a = new User(7);
		User b = new User(7);
		Set<User> users = new HashSet<>();
		users.add(a);
		users.add(b);
		System.out.println(a.equals(b));
		System.out.println(a.hashCode());
		System.out.println(b.hashCode());
		System.out.println(users.size());
	}
}
