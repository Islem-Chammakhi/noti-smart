"use client";
import { File, Trash, Trash2, Upload } from "lucide-react";
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
import { axiosFileInstance } from "@/lib/axios-config";

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
    setFiles([...files, ...newFiles]);
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

  const handleClear = () => {
    setFiles([]);
  };

  const handleUpload = async () => {
    if (files.length === 0 || uploading) return;

    setUploading(true);
    setMsg("");
    const uploadPromises = files.map(async (fileWithProgress) => {
      const formData = new FormData();
      formData.append("file", fileWithProgress.file);

      try {
        const response = await axiosFileInstance.post(
          "/admin/upload_marks",
          formData,
          {
            onUploadProgress: (progressEvent) => {
              const progress =
                Math.round(progressEvent.loaded * 100) /
                (progressEvent.total || 1);
              setFiles((prevFiles) =>
                prevFiles.map((file) =>
                  file.id === fileWithProgress.id ? { ...file, progress } : file
                )
              );
            },
          }
        );
        if (response.status === 200) {
          console.log(response.data);
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
        console.log(error);
        setMsg(error?.message || String(error));
      }
    });
    await Promise.all(uploadPromises); // run promises at the same time !
    setUploading(false);
  };

  return (
    <div className="bg-white shadow-lg rounded-2xl p-8 space-y-2 flex flex-row justify-between items-center gap-12">
      <div
        {...getRootProps()}
        className="w-[500px] h-[500px] min-h-fit border-2 border-dashed rounded-md flex items-center justify-center cursor-pointer transition-all hover:border-gray-900"
      >
        <input {...getInputProps()} disabled={uploading} />
        <div className="flex flex-col items-center justify-center">
          <div className="h-10 w-10 rounded-full bg-indigo-50 flex items-center justify-center mb-1">
            <Upload className="" />
          </div>
          {isDragActive ? (
            <p className="text-gray-900 font-medium">
              Déposez le fichier ici ...
            </p>
          ) : (
            <div className="flex flex-col items-center justify-center">
              <p className="text-gray-900 text-center text-lg font-medium">
                Glissez et déposez un fichier Excel ou cliquez pour sélectionner
              </p>
              <p className="text-sm text-muted-foreground mt-1">
                seuls les fichiers xl sont supportés !
              </p>
            </div>
          )}
        </div>
      </div>
      {files.length !== 0 && (
        <div className="flex flex-col items-center justify-center space-y-2">
          <FileList files={files} onRemove={removeFile} uploading={uploading} />
          <div className="flex flex-row justify-between items-center gap-8">
            <ActionButtons
              onClear={handleClear}
              onUpload={handleUpload}
              disabled={files.length === 0 || uploading}
            />
          </div>
        </div>
      )}
      <span className="text-red-800 text-sm font-semibold">{msg}</span>
    </div>
  );
};

export default FileUpload;

type ActionButtonsProps = {
  disabled: boolean;
  onUpload: () => void;
  onClear: () => void;
};

function ActionButtons({ onUpload, onClear, disabled }: ActionButtonsProps) {
  return (
    <>
      <Button onClick={onUpload} disabled={disabled} className="cursor-pointer">
        <Upload size={18} />
        Envoyer tous
      </Button>
      <Button onClick={onClear} disabled={disabled}>
        <Trash2 size={18} />
        Tout effacer
      </Button>
    </>
  );
}

type FileListProps = {
  files: FileWithProgress[];
  onRemove: (id: string) => void;
  uploading: boolean;
};

const formatFileSize = (bytes: number) => {
  if (bytes === 0) return "0 B";
  const k = 1024;
  const sizes = ["B", "KB", "MB", "GB"];
  const i = Math.floor(Math.log(bytes) / Math.log(k));
  return `${parseFloat((bytes / Math.pow(k, i)).toFixed(1))} ${sizes[i]}`;
};

const FileList = ({ files, onRemove, uploading }: FileListProps) => {
  if (files.length === 0) return;
  return (
    <div className="flex items-center justify-center">
      <div className="space-y-2">
        {files.map((file) => (
          <FileItem
            key={file.id}
            file={file}
            onRemove={onRemove}
            uploading={uploading}
          />
        ))}
      </div>
    </div>
  );
};

type FileItemProps = {
  file: FileWithProgress;
  onRemove: (id: string) => void;
  uploading: boolean;
};
const FileItem = ({ file, onRemove, uploading }: FileItemProps) => {
  return (
    <div className="flex min-w-full w-[500px] max-w-lg flex-col gap-6">
      <Item variant="outline">
        <ItemMedia variant="icon">
          <File />
        </ItemMedia>
        <ItemContent>
          <ItemTitle>{file.id}</ItemTitle>
          <ItemDescription>{formatFileSize(file.file.size)}</ItemDescription>
        </ItemContent>

        {!uploading && (
          <ItemActions>
            <Button
              size="sm"
              variant="destructive"
              onClick={() => onRemove(file.id)}
            >
              <Trash />
            </Button>
          </ItemActions>
        )}
        <Progress value={file.progress} />
      </Item>
    </div>
  );
};
