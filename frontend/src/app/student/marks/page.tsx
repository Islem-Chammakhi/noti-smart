"use client";
import { useEffect, useState } from "react";
import MarksTable from "@/components/MarksTable";
import myApi from "@/lib/api";
import { useAuth } from "@/hooks/useAuth";
import Loader from "@/components/Loader";

const columns = ["#", "matiére", "DS", "TP/ORALE", "EXAM"];

export default function SubjectGradesPage() {
  const { user } = useAuth();
  const [data, setData] = useState<StudentMarksPerSubject[]>([]);
  const [loading, setLoading] = useState(false);
  const fetchMarks = async (userCin: string) => {
    try {
      setLoading(true);
      const response = await myApi.getStudentMarks(userCin);
      if (response.status === 200) {
        setData(response.data);
      }
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };
  useEffect(() => {
    user && fetchMarks(user.cin);
  }, []);

  return (
    <div className="p-6 space-y-6">
      <h2 className="text-2xl font-semibold text-gray-800">
        Les notes disponible
      </h2>

      {/* --- Tableau des notes --- */}
      <div>
        {loading ? (
          <Loader />
        ) : data.length > 0 ? (
          <MarksTable
            columns={columns}
            title={`Les Notes de l'étudiant — ${user?.nom}`}
            rows={data}
          />
        ) : (
          <p className="text-center text-gray-500 py-6">
            Aucune donnée à afficher
          </p>
        )}
      </div>
    </div>
  );
}

// {/* --- Filtres --- */}
// {/* <Card>
//   <CardHeader>
//     <CardTitle>Filtrer les résultats</CardTitle>
//   </CardHeader>
//   <CardContent>
//     <div className="flex flex-col md:flex-row gap-4">
//       {/* Select Filière */}
//       <Select onValueChange={setSelectedFiliere}>
//         <SelectTrigger className="w-[220px]">
//           <SelectValue placeholder="Choisir une filière" />
//         </SelectTrigger>
//         <SelectContent>
//           {filieres.map((f) => (
//             <SelectItem key={f} value={f}>
//               {f}
//             </SelectItem>
//           ))}
//         </SelectContent>
//       </Select>

//       {/* Select Matière */}
//       <Select
//         onValueChange={setSelectedMatiere}
//         disabled={!selectedFiliere}
//       >
//         <SelectTrigger className="w-[220px]">
//           <SelectValue placeholder="Choisir une matière" />
//         </SelectTrigger>
//         <SelectContent>
//           {matieres.map((m) => (
//             <SelectItem key={m} value={m}>
//               {m}
//             </SelectItem>
//           ))}
//         </SelectContent>
//       </Select>
//     </div>
//   </CardContent>
// </Card> */}
