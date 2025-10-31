"use client";

import { File, Trash2, Upload, CheckCircle2, XCircle } from "lucide-react";
import { useCallback, useState } from "react";
import { useDropzone } from "react-dropzone";
import { Button } from "./ui/button";
import {
  Item,
  ItemActions,
  ItemContent,
  ItemDescription,
  ItemMedia,
  ItemTitle,
} from "@/components/ui/item";
import { Progress } from "./ui/progress";
import { axiosInstance } from "@/lib/axios-config";
import { cn } from "@/lib/utils";

type FileWithProgress = {
  id: string;
  file: File;
  progress: number;
  uploaded: boolean;
};

const FileUpload = () => {
  const [files, setFiles] = useState<FileWithProgress[]>([]);
  const [uploading, setUploading] = useState(false);
  const [msg, setMsg] = useState("");

  const onDrop = useCallback((acceptedFiles: File[]) => {
    if (acceptedFiles.length === 0) return;
    const newFiles = acceptedFiles.map((file) => ({
      file,
      progress: 0,
      uploaded: false,
      id: file.name,
    }));
    setFiles((prev) => [...prev, ...newFiles]);
  }, []);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: {
      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet": [
        ".xlsx",
      ],
      "application/vnd.ms-excel": [".xls"],
    },
    maxFiles: 5,
    maxSize: 10 * 1024 * 1024,
  });

  const removeFile = (id: string) => {
    setFiles((prevFiles) => prevFiles.filter((file) => file.id !== id));
  };

  const handleClear = () => setFiles([]);

  const handleUpload = async () => {
    if (files.length === 0 || uploading) return;

    setUploading(true);
    setMsg("");
    const uploadPromises = files.map(async (fileWithProgress) => {
      const formData = new FormData();
      formData.append("file", fileWithProgress.file);

      try {
        const response = await axiosInstance.post(
          "/admin/upload_marks",
          formData,
          {
            onUploadProgress: (progressEvent) => {
              const progress = Math.round(
                (progressEvent.loaded * 100) / (progressEvent.total || 1)
              );
              setFiles((prevFiles) =>
                prevFiles.map((file) =>
                  file.id === fileWithProgress.id ? { ...file, progress } : file
                )
              );
            },
            headers: { "Content-Type": "multipart/form-data" },
          }
        );

        if (response.status === 200) {
          setFiles((prevFiles) =>
            prevFiles.map((file) =>
              file.id === fileWithProgress.id
                ? { ...file, uploaded: true }
                : file
            )
          );
        } else {
          setMsg(response.data);
        }
      } catch (error: any) {

        setMsg(error?.response.data.message || String(error));
      }
    });

    await Promise.all(uploadPromises);
    setUploading(false);
  };

  return (
    <div className="bg-white/80 backdrop-blur-lg shadow-lg rounded-3xl p-10 flex flex-col md:flex-row items-center justify-between gap-10 border border-blue-100">
      {/* Zone de drop */}
      <div
        {...getRootProps()}
        className={cn(
          "w-[450px] h-[300px] border-2 border-dashed rounded-2xl flex flex-col items-center justify-center cursor-pointer transition-all duration-300",
          isDragActive
            ? "border-blue-500 bg-blue-50/50 scale-[1.02]"
            : "border-gray-300 hover:border-blue-400 hover:bg-blue-50/30"
        )}
      >
        <input {...getInputProps()} disabled={uploading} />
        <div className="flex flex-col items-center justify-center text-center space-y-2">
          <div className="h-14 w-14 rounded-full bg-blue-100 flex items-center justify-center">
            <Upload className="text-blue-600" size={24} />
          </div>
          <p className="text-gray-800 font-medium">
            {isDragActive
              ? "Déposez vos fichiers ici..."
              : "Glissez-déposez vos fichiers Excel ou cliquez pour sélectionner"}
          </p>
          <p className="text-sm text-gray-500">
            (Formats acceptés : .xls, .xlsx)
          </p>
        </div>
      </div>

      {/* Liste + actions */}
      {files.length > 0 && (
        <div className="flex flex-col items-center justify-center w-full max-w-lg space-y-4">
          <FileList files={files} onRemove={removeFile} uploading={uploading} />
          <div className="flex flex-row justify-center items-center gap-4">
            <Button
              onClick={handleUpload}
              disabled={uploading}
              className="bg-blue-600 hover:bg-blue-700 text-white shadow-md"
            >
              <Upload size={18} className="mr-1" />
              Envoyer tous
            </Button>
            <Button
              onClick={handleClear}
              disabled={uploading}
              variant="destructive"
              className="shadow-md"
            >
              <Trash2 size={18} className="mr-1" />
              Tout effacer
            </Button>
          </div>
        </div>
      )}

      {/* Message d’erreur */}
      {msg && (
        <div className="absolute bottom-6 left-1/2 -translate-x-1/2">
          <div className="bg-red-100 text-red-700 px-4 py-2 rounded-lg shadow-sm border border-red-200">
            {msg}
          </div>
        </div>
      )}
    </div>
  );
};

export default FileUpload;

/* ---------- Sous-composants ---------- */

const formatFileSize = (bytes: number) => {
  if (bytes === 0) return "0 B";
  const k = 1024;
  const sizes = ["B", "KB", "MB", "GB"];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return `${parseFloat((bytes / Math.pow(k, i)).toFixed(1))} ${sizes[i]}`;
};

// type FileWithProgress = {
//   id: string;
//   file: File;
//   progress: number;
//   uploaded: boolean;
// };

type FileListProps = {
  files: FileWithProgress[];
  onRemove: (id: string) => void;
  uploading: boolean;
};

const FileList = ({ files, onRemove, uploading }: FileListProps) => {
  return (
    <div className="w-full space-y-3">
      {files.map((file) => (
        <div>
          <Item className="border border-gray-200 rounded-xl shadow-sm bg-white/70 backdrop-blur-md hover:shadow-md transition-all duration-200">
            <ItemMedia variant="icon">
              {file.uploaded ? (
                <CheckCircle2 className="text-green-500" />
              ) : (
                <File className="text-blue-600" />
              )}
            </ItemMedia>
            <ItemContent>
              <ItemTitle className="text-gray-800 font-medium">
                {file.id}
              </ItemTitle>
              <ItemDescription className="text-gray-500 text-sm">
                {formatFileSize(file.file.size)}
              </ItemDescription>
            </ItemContent>
            {!uploading && !file.uploaded && (
              <ItemActions>
                <Button
                  size="sm"
                  variant="destructive"
                  onClick={() => onRemove(file.id)}
                  className="hover:scale-105 transition-transform"
                >
                  <XCircle size={16} />
                </Button>
              </ItemActions>
            )}
            <Progress
              value={file.progress}
              className={cn(
                "h-2 mt-2 rounded-full",
                file.uploaded
                  ? "bg-green-200"
                  : "bg-blue-200 [&>div]:bg-blue-600"
              )}
            />
          </Item>
        </div>
      ))}
    </div>
  );
};
