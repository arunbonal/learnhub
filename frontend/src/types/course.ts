export interface Course {
  id: string;
  title: string;
  description: string;
  instructor: string;
  imageUrl: string;
  category: string;
  durationHours: number;
  price: number;
  topics: string[];
  difficulty: string;
  enrolledStudents: number;
  rating: number;
  createdAt?: string;
  updatedAt?: string;
} 