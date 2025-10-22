"use client";
import { useState } from "react";
import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import {
  CartesianGrid,
  Line,
  LineChart,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";

export default function AverageSubjectsPerFiliere() {
  const [data, setData] = useState([]);
  const fakeStats = [
    { name: "Algorithmique", moyenne: 12.5 },
    { name: "Compilation", moyenne: 14.2 },
    { name: "Systèmes d’exploitation", moyenne: 11.8 },
    { name: "Base de données", moyenne: 15.3 },
    { name: "Programmation Web", moyenne: 16.0 },

    { name: "UML", moyenne: 13.1 },
    { name: "Génie logiciel", moyenne: 14.7 },
    { name: "Gestion de projet", moyenne: 15.5 },
    { name: "Test logiciel", moyenne: 12.9 },
    { name: "Développement mobile", moyenne: 16.8 },

    { name: "Réseaux 1", moyenne: 13.8 },
  ];

  //   useEffect(() => {
  //     if (!filiere) return;
  //     // Exemple de requête vers ton backend
  //     fetch(`/api/stats/moyennes?filiere=${filiere}`)
  //       .then(res => res.json())
  //       .then(setData)
  //       .catch(err => console.error(err));
  //   }, [filiere]);

  return (
    <>
      {fakeStats && (
        <div>
          <Card className="shadow-md border border-indigo-100">
            <CardHeader>
              <CardTitle className="text-lg">
                Moyennes générales — {"ING_2 INFO"}
              </CardTitle>
            </CardHeader>
            <CardContent>
              <div className="h-[350px] min-w-max">
                <LineChart
                  style={{
                    width: "100%",
                    maxHeight: "40vh",
                    aspectRatio: 1.618,
                  }}
                  responsive
                  data={fakeStats}
                  margin={{
                    top: 10,
                    right: 30,
                    left: 0,
                    bottom: 0,
                  }}
                >
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis
                    dataKey="name"
                    interval={0}
                    angle={-15}
                    textAnchor="end"
                    height={80}
                  />
                  <YAxis />
                  <Tooltip />
                  <Line
                    type="monotone"
                    dataKey="moyenne"
                    stroke="#8884d8"
                    fill="#8884d8"
                  />
                </LineChart>
              </div>
            </CardContent>
          </Card>
        </div>
      )}
    </>
  );
}
