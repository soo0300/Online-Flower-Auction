import { Link } from "react-router-dom";

type Props = {
  path: string;
  str: string;
}

const ActionButton = ({ path, str }: Props) => {
  return (
    <Link to={path}
      className="rounded-md bg-secondary-500 px-10 py-2 hover:bg-primary-500 hover:text-white"
    >
      {str}
    </Link>
  )
}

export default ActionButton